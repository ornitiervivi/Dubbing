package com.clouddubber.domain.model;

import java.time.Instant;

public class DubbingJob {
    public String id;
    public String originalFileName;
    public String originalContentType;
    public Enums.DubbingSourceType sourceType;
    public Enums.DubbingJobStatus status;
    public String sourceAsset;
    public String extractedAudioAsset;
    public String originalTranscriptAsset;
    public String translatedScriptAsset;
    public String dubbedAudioAsset;
    public String renderedVideoAsset;
    public String failureReason;
    public Instant createdAt;
    public Instant updatedAt;

    public DubbingJob(String id, String originalFileName, String originalContentType, Enums.DubbingSourceType sourceType, Enums.DubbingJobStatus status, String sourceAsset, String extractedAudioAsset, String originalTranscriptAsset, String translatedScriptAsset, String dubbedAudioAsset, String renderedVideoAsset, String failureReason, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.originalFileName = originalFileName;
        this.originalContentType = originalContentType;
        this.sourceType = sourceType;
        this.status = status;
        this.sourceAsset = sourceAsset;
        this.extractedAudioAsset = extractedAudioAsset;
        this.originalTranscriptAsset = originalTranscriptAsset;
        this.translatedScriptAsset = translatedScriptAsset;
        this.dubbedAudioAsset = dubbedAudioAsset;
        this.renderedVideoAsset = renderedVideoAsset;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
