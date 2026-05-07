package com.clouddubber.application;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.TranslationModels;
import com.clouddubber.domain.port.Ports;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranslationPipelineService {
    private final Ports.DubbingJobRepository jobs;
    private final Ports.DubbingSegmentRepository segments;
    private final Ports.ObjectStorageGateway storage;
    private final Ports.ScriptTranslationGateway translationGateway;
    private final Ports.ClockGateway clock;
    private final String sourceLanguage;
    private final String targetLanguage;
    private final List<TranslationModels.PreservedTerm> preservedTerms;
    private final boolean storeTranslatedScriptAsset;
    private final String outputFormat;

    public TranslationPipelineService(Ports.DubbingJobRepository jobs, Ports.DubbingSegmentRepository segments, Ports.ObjectStorageGateway storage, Ports.ScriptTranslationGateway translationGateway, Ports.ClockGateway clock, String sourceLanguage, String targetLanguage, List<TranslationModels.PreservedTerm> preservedTerms, boolean storeTranslatedScriptAsset, String outputFormat) {
        this.jobs = jobs; this.segments = segments; this.storage = storage; this.translationGateway = translationGateway; this.clock = clock; this.sourceLanguage = sourceLanguage; this.targetLanguage = targetLanguage; this.preservedTerms = preservedTerms; this.storeTranslatedScriptAsset = storeTranslatedScriptAsset; this.outputFormat = outputFormat;
    }

    public void processPendingTranslations(int batchSize) { jobs.findByStatus(Enums.DubbingJobStatus.TRANSLATION_PENDING, batchSize).forEach(this::translateDubbingJobScript); }

    public void translateDubbingJobScript(DubbingJob job) {
        if (job == null) throw new IllegalArgumentException("Job not found");
        if (job.status != Enums.DubbingJobStatus.TRANSLATION_PENDING) throw new IllegalStateException("Invalid status");
        List<DubbingSegment> transcribed = segments.findByJobId(job.id).stream().filter(s -> s.status == Enums.DubbingSegmentStatus.TRANSCRIBED).toList();
        if (transcribed.isEmpty()) throw new IllegalStateException("Missing transcribed segments");
        job.status = Enums.DubbingJobStatus.TRANSLATION_RUNNING; job.updatedAt = clock.now(); jobs.save(job);
        try {
            var reqSegments = transcribed.stream().map(s -> new TranslationModels.ScriptSegmentToTranslate(s.id, s.segmentIndex, s.startTime, s.endTime, s.originalText)).toList();
            var result = translationGateway.translate(new TranslationModels.ScriptTranslationRequest(job.id, sourceLanguage, targetLanguage, preservedTerms, reqSegments));
            if (result.segments().size() != transcribed.size()) throw new IllegalStateException("Mismatched segments");
            Map<Integer, TranslationModels.TranslatedScriptSegment> byIndex = new HashMap<>();
            for (var it : result.segments()) {
                if (it.translatedText() == null || it.translatedText().isBlank()) throw new IllegalStateException("Empty translatedText");
                if (it.adaptedText() == null || it.adaptedText().isBlank()) throw new IllegalStateException("Empty adaptedText");
                if (byIndex.put(it.segmentIndex(), it) != null) throw new IllegalStateException("Duplicated segmentIndex");
            }
            for (DubbingSegment s : transcribed) {
                var out = byIndex.get(s.segmentIndex);
                if (out == null) throw new IllegalStateException("Missing segmentIndex");
                if (s.adaptedText != null && !s.adaptedText.isBlank()) throw new IllegalStateException("Manual adaptation already exists");
                s.translatedText = out.translatedText();
                s.adaptedText = out.adaptedText();
                s.status = Enums.DubbingSegmentStatus.ADAPTED;
                segments.save(s);
            }
            if (storeTranslatedScriptAsset) {
                String body = "{\"jobId\":\"" + job.id + "\",\"sourceLanguage\":\"" + sourceLanguage + "\",\"targetLanguage\":\"" + targetLanguage + "\",\"segments\":" + result.segments().size() + "}";
                String key = storage.upload("jobs/" + job.id + "/translated-script." + outputFormat, new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)), body.getBytes(StandardCharsets.UTF_8).length, "application/json");
                job.translatedScriptAsset = key;
            }
            job.status = Enums.DubbingJobStatus.TRANSLATION_COMPLETED; job.updatedAt = clock.now(); jobs.save(job);
            job.status = Enums.DubbingJobStatus.VOICE_GENERATION_PENDING; job.updatedAt = clock.now(); jobs.save(job);
        } catch (Exception ex) {
            job.status = Enums.DubbingJobStatus.FAILED; job.failureReason = sanitize(ex.getMessage()); job.updatedAt = clock.now(); jobs.save(job);
        }
    }

    private String sanitize(String reason){ if(reason == null) return "TRANSLATION_FAILED"; String noPath = reason.replaceAll("([A-Za-z]:)?[/\\\\][^\\s]+", "[redacted]"); return noPath.substring(0, Math.min(noPath.length(), 300)); }
}
