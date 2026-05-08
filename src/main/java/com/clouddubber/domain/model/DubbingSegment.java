package com.clouddubber.domain.model;

public class DubbingSegment {
    public String id;
    public String jobId;
    public Integer segmentIndex;
    public Double startTime;
    public Double endTime;
    public String originalText;
    public String translatedText;
    public String adaptedText;
    public Enums.DubbingSegmentStatus status;
    public String failureReason;

    public DubbingSegment(String id, String jobId, Integer segmentIndex, Double startTime, Double endTime, String originalText, String translatedText, String adaptedText, Enums.DubbingSegmentStatus status, String failureReason) {
        this.id = id;
        this.jobId = jobId;
        this.segmentIndex = segmentIndex;
        this.startTime = startTime;
        this.endTime = endTime;
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.adaptedText = adaptedText;
        this.status = status;
        this.failureReason = failureReason;
    }

    public boolean belongsToJob(String expectedJobId) {
        return jobId != null && jobId.equals(expectedJobId);
    }

    public void adapt(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Adapted text is required");
        }
        adaptedText = text;
        status = Enums.DubbingSegmentStatus.ADAPTED;
    }
}
