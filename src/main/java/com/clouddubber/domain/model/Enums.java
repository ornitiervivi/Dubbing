package com.clouddubber.domain.model;

public final class Enums {
    private Enums() {}
    public enum DubbingJobStatus { CREATED, AUDIO_EXTRACTION_PENDING, FAILED, COMPLETED }
    public enum DubbingSegmentStatus { PENDING, ADAPTED }
    public enum DubbingSourceType { UPLOAD }
    public enum VoiceProfileStatus { ACTIVE, DISABLED }
}
