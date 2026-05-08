package com.clouddubber.domain.model;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public final class SpeechModels {
    private SpeechModels() {}

    public static final class SpeechToTextRequest {
        private final String audioAsset;
        private final Path audioFile;
        private final String languageHint;
        private final String jobId;
        private final Map<String, String> metadata;

        public SpeechToTextRequest(String audioAsset, Path audioFile, String languageHint, String jobId, Map<String, String> metadata) {
            this.audioAsset = audioAsset;
            this.audioFile = audioFile;
            this.languageHint = languageHint;
            this.jobId = jobId;
            this.metadata = metadata;
        }

        public String audioAsset() { return audioAsset; }
        public Path audioFile() { return audioFile; }
        public String languageHint() { return languageHint; }
        public String jobId() { return jobId; }
        public Map<String, String> metadata() { return metadata; }
    }

    public static final class TranscribedSegment {
        private final int index;
        private final double startTime;
        private final double endTime;
        private final String text;

        public TranscribedSegment(int index, double startTime, double endTime, String text) {
            this.index = index;
            this.startTime = startTime;
            this.endTime = endTime;
            this.text = text;
        }

        public int index() { return index; }
        public double startTime() { return startTime; }
        public double endTime() { return endTime; }
        public String text() { return text; }
    }

    public static final class SpeechToTextResult {
        private final String language;
        private final double duration;
        private final List<TranscribedSegment> segments;

        public SpeechToTextResult(String language, double duration, List<TranscribedSegment> segments) {
            this.language = language;
            this.duration = duration;
            this.segments = segments;
        }

        public String language() { return language; }
        public double duration() { return duration; }
        public List<TranscribedSegment> segments() { return segments; }
    }
}
