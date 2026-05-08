package com.clouddubber.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "dubbing_jobs")
class DubbingJobEntity {
    @Id
    String id;
    String originalFilename;
    String contentType;
    String sourceType;
    String sourceAsset;
    String extractedAudioAsset;
    String originalTranscriptAsset;
    String translatedScriptAsset;
    String dubbedAudioAsset;
    String renderedVideoAsset;
    String failureReason;
    String status;
    Instant createdAt;
    Instant updatedAt;
}
