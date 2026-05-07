package com.clouddubber.domain.model;

import java.time.Instant;

public class DubbingJob {
    public String id;
    public String originalFilename;
    public String contentType;
    public long sizeBytes;
    public String storageKey;
    public Enums.DubbingJobStatus status;
    public Instant createdAt;

    public DubbingJob(String id, String originalFilename, String contentType, long sizeBytes, String storageKey, Enums.DubbingJobStatus status, Instant createdAt) {
        this.id = id; this.originalFilename = originalFilename; this.contentType = contentType; this.sizeBytes = sizeBytes; this.storageKey = storageKey; this.status = status; this.createdAt = createdAt;
    }
}
