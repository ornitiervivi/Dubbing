package com.clouddubber.application;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.port.Ports;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;

public class AudioExtractionPipelineService {
    private final Ports.DubbingJobRepository jobs;
    private final Ports.ObjectStorageGateway storage;
    private final Ports.AudioExtractionGateway extractionGateway;
    private final Ports.ClockGateway clock;
    private final String audioFormat;
    private final String audioCodec;
    private final Duration timeout;
    private final Path tempDirectory;

    public AudioExtractionPipelineService(Ports.DubbingJobRepository jobs, Ports.ObjectStorageGateway storage, Ports.AudioExtractionGateway extractionGateway, Ports.ClockGateway clock, String audioFormat, String audioCodec, Duration timeout, Path tempDirectory) {
        this.jobs = jobs;
        this.storage = storage;
        this.extractionGateway = extractionGateway;
        this.clock = clock;
        this.audioFormat = audioFormat;
        this.audioCodec = audioCodec;
        this.timeout = timeout;
        this.tempDirectory = tempDirectory;
    }

    public void processPendingAudioExtractions(int batchSize) {
        List<DubbingJob> pending = jobs.findByStatus(Enums.DubbingJobStatus.AUDIO_EXTRACTION_PENDING, batchSize);
        pending.forEach(this::extractDubbingJobAudio);
    }

    public void extractDubbingJobAudio(DubbingJob job) {
        if (job == null) throw new IllegalArgumentException("Job not found");
        if (job.status != Enums.DubbingJobStatus.AUDIO_EXTRACTION_PENDING) throw new IllegalStateException("Invalid status");
        if (job.sourceAsset == null || !job.originalContentType.startsWith("video/")) throw new IllegalStateException("Invalid source");
        job.status = Enums.DubbingJobStatus.AUDIO_EXTRACTION_RUNNING;
        job.updatedAt = clock.now();
        jobs.save(job);
        try {
            Path input = storage.downloadToTempFile(job.sourceAsset, tempDirectory);
            Path output = Files.createTempFile(tempDirectory, "audio-", "." + audioFormat);
            Ports.AudioExtractionResult result = extractionGateway.extractAudio(new Ports.AudioExtractionRequest(input, output, audioFormat, audioCodec, timeout));
            Ports.AssetReference ref = storage.uploadFile("jobs/" + job.id + "/extracted-audio." + audioFormat, result.outputFile(), result.contentType());
            job.extractedAudioAsset = ref.key();
            job.status = Enums.DubbingJobStatus.AUDIO_EXTRACTION_COMPLETED;
            job.updatedAt = clock.now();
            jobs.save(job);
            job.status = Enums.DubbingJobStatus.TRANSCRIPTION_PENDING;
            job.updatedAt = clock.now();
            jobs.save(job);
        } catch (Exception ex) {
            job.status = Enums.DubbingJobStatus.FAILED;
            job.failureReason = sanitize(ex.getMessage());
            job.updatedAt = clock.now();
            jobs.save(job);
        }
    }

    private String sanitize(String reason) {
        if (reason == null) return "AUDIO_EXTRACTION_FAILED";
        String noPath = reason.replaceAll("([A-Za-z]:)?[/\\][^\\s]+", "[redacted]");
        return noPath.substring(0, Math.min(300, noPath.length()));
    }
}
