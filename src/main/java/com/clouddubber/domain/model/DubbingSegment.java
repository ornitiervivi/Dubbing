package com.clouddubber.domain.model;

public class DubbingSegment {
    public String id;
    public String jobId;
    public String adaptedText;
    public Enums.DubbingSegmentStatus status;

    public DubbingSegment(String id, String jobId, String adaptedText, Enums.DubbingSegmentStatus status) { this.id = id; this.jobId = jobId; this.adaptedText = adaptedText; this.status = status; }
}
