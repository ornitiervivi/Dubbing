package com.clouddubber.domain.model;

public final class Enums {
    private Enums() {}
    public enum DubbingJobStatus { CREATED, AUDIO_EXTRACTION_PENDING, AUDIO_EXTRACTION_RUNNING, AUDIO_EXTRACTION_COMPLETED, TRANSCRIPTION_PENDING, FAILED, COMPLETED }
    public enum DubbingSegmentStatus { PENDING, ADAPTED }
    public enum DubbingSourceType { UPLOAD }
    public enum VoiceProfileStatus { ACTIVE, DISABLED }
}
