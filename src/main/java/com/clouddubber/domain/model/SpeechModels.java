package com.clouddubber.domain.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public final class SpeechModels {
    private SpeechModels() {}
    public record SpeechToTextRequest(String audioAsset, Path audioFile, String languageHint, String jobId, Map<String, String> metadata) {}
    public record TranscribedSegment(int index, double startTime, double endTime, String text) {}
    public record SpeechToTextResult(String language, double duration, List<TranscribedSegment> segments) {}
}
