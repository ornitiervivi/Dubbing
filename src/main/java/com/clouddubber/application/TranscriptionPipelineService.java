package com.clouddubber.application;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.SpeechModels;
import com.clouddubber.domain.port.Ports;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.util.List;

public class TranscriptionPipelineService {
    private final Ports.DubbingJobRepository jobs;
    private final Ports.DubbingSegmentRepository segments;
    private final Ports.ObjectStorageGateway storage;
    private final Ports.SpeechToTextGateway stt;
    private final Ports.ClockGateway clock;
    private final String languageHint;
    private final boolean storeTranscriptAsset;
    private final String outputFormat;
    private final Path tempDirectory;

    public TranscriptionPipelineService(Ports.DubbingJobRepository jobs, Ports.DubbingSegmentRepository segments, Ports.ObjectStorageGateway storage, Ports.SpeechToTextGateway stt, Ports.ClockGateway clock, String languageHint, boolean storeTranscriptAsset, String outputFormat, Path tempDirectory) {
        this.jobs = jobs; this.segments = segments; this.storage = storage; this.stt = stt; this.clock = clock; this.languageHint = languageHint; this.storeTranscriptAsset = storeTranscriptAsset; this.outputFormat = outputFormat; this.tempDirectory = tempDirectory;
    }

    public void processPendingTranscriptions(int batchSize){ jobs.findByStatus(Enums.DubbingJobStatus.TRANSCRIPTION_PENDING, batchSize).forEach(this::transcribeDubbingJobAudio); }

    public void transcribeDubbingJobAudio(DubbingJob job){
        if(job == null) throw new IllegalArgumentException("Job not found");
        if(job.status != Enums.DubbingJobStatus.TRANSCRIPTION_PENDING) throw new IllegalStateException("Invalid status");
        if(job.extractedAudioAsset == null || job.extractedAudioAsset.isBlank()) throw new IllegalStateException("Missing extractedAudioAsset");
        job.status = Enums.DubbingJobStatus.TRANSCRIPTION_RUNNING; job.updatedAt = clock.now(); jobs.save(job);
        try{
            Path audioFile = storage.downloadToTempFile(job.extractedAudioAsset, tempDirectory);
            SpeechModels.SpeechToTextResult result = stt.transcribe(new SpeechModels.SpeechToTextRequest(job.extractedAudioAsset, audioFile, languageHint, job.id, java.util.Map.of()));
            for (SpeechModels.TranscribedSegment it: result.segments()) {
                if(it.text()==null || it.text().isBlank()) throw new IllegalStateException("Empty segment text");
                if(it.startTime()<0 || it.endTime()<=it.startTime()) throw new IllegalStateException("Invalid timestamp");
                if(segments.existsByJobIdAndSegmentIndex(job.id, it.index())) throw new IllegalStateException("Duplicated segment");
                segments.save(new DubbingSegment(java.util.UUID.randomUUID().toString(), job.id, it.index(), it.startTime(), it.endTime(), it.text(), null, null, Enums.DubbingSegmentStatus.TRANSCRIBED, null));
            }
            if(storeTranscriptAsset){
                String body = "{\"language\":\""+result.language()+"\",\"duration\":"+result.duration()+",\"segments\":"+result.segments().size()+"}";
                String key = storage.upload("jobs/"+job.id+"/transcript."+outputFormat, new ByteArrayInputStream(body.getBytes()), body.getBytes().length, "application/json");
                job.originalTranscriptAsset = key;
            }
            job.status = Enums.DubbingJobStatus.TRANSCRIPTION_COMPLETED; job.updatedAt = clock.now(); jobs.save(job);
            job.status = Enums.DubbingJobStatus.TRANSLATION_PENDING; job.updatedAt = clock.now(); jobs.save(job);
        }catch (Exception ex){
            job.status = Enums.DubbingJobStatus.FAILED; job.failureReason = sanitize(ex.getMessage()); job.updatedAt = clock.now(); jobs.save(job);
        }
    }
    private String sanitize(String reason){ if(reason == null) return "TRANSCRIPTION_FAILED"; String noPath = reason.replaceAll("([A-Za-z]:)?[/\\][^\\s]+", "[redacted]"); return noPath.substring(0, Math.min(noPath.length(), 300)); }
}
