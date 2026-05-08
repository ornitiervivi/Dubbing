package com.clouddubber.domain.model;

import java.util.List;

public final class TranslationModels {
    private TranslationModels() {}

    public static final class PreservedTerm {
        private final String value;

        public PreservedTerm(String value) { this.value = value; }
        public String value() { return value; }
    }

    public static final class ScriptSegmentToTranslate {
        private final String segmentId;
        private final int segmentIndex;
        private final double startTime;
        private final double endTime;
        private final String originalText;

        public ScriptSegmentToTranslate(String segmentId, int segmentIndex, double startTime, double endTime, String originalText) {
            this.segmentId = segmentId;
            this.segmentIndex = segmentIndex;
            this.startTime = startTime;
            this.endTime = endTime;
            this.originalText = originalText;
        }

        public String segmentId() { return segmentId; }
        public int segmentIndex() { return segmentIndex; }
        public double startTime() { return startTime; }
        public double endTime() { return endTime; }
        public String originalText() { return originalText; }
    }

    public static final class ScriptTranslationRequest {
        private final String jobId;
        private final String sourceLanguage;
        private final String targetLanguage;
        private final List<PreservedTerm> preservedTerms;
        private final List<ScriptSegmentToTranslate> segments;

        public ScriptTranslationRequest(String jobId, String sourceLanguage, String targetLanguage, List<PreservedTerm> preservedTerms, List<ScriptSegmentToTranslate> segments) {
            this.jobId = jobId;
            this.sourceLanguage = sourceLanguage;
            this.targetLanguage = targetLanguage;
            this.preservedTerms = preservedTerms;
            this.segments = segments;
        }

        public String jobId() { return jobId; }
        public String sourceLanguage() { return sourceLanguage; }
        public String targetLanguage() { return targetLanguage; }
        public List<PreservedTerm> preservedTerms() { return preservedTerms; }
        public List<ScriptSegmentToTranslate> segments() { return segments; }
    }

    public static final class TranslatedScriptSegment {
        private final String segmentId;
        private final int segmentIndex;
        private final String originalText;
        private final String translatedText;
        private final String adaptedText;
        private final List<String> preservedTermsUsed;
        private final Double estimatedReadingDuration;
        private final List<String> warnings;

        public TranslatedScriptSegment(String segmentId, int segmentIndex, String originalText, String translatedText, String adaptedText, List<String> preservedTermsUsed, Double estimatedReadingDuration, List<String> warnings) {
            this.segmentId = segmentId;
            this.segmentIndex = segmentIndex;
            this.originalText = originalText;
            this.translatedText = translatedText;
            this.adaptedText = adaptedText;
            this.preservedTermsUsed = preservedTermsUsed;
            this.estimatedReadingDuration = estimatedReadingDuration;
            this.warnings = warnings;
        }

        public String segmentId() { return segmentId; }
        public int segmentIndex() { return segmentIndex; }
        public String originalText() { return originalText; }
        public String translatedText() { return translatedText; }
        public String adaptedText() { return adaptedText; }
        public List<String> preservedTermsUsed() { return preservedTermsUsed; }
        public Double estimatedReadingDuration() { return estimatedReadingDuration; }
        public List<String> warnings() { return warnings; }
    }

    public static final class ScriptTranslationResult {
        private final List<TranslatedScriptSegment> segments;

        public ScriptTranslationResult(List<TranslatedScriptSegment> segments) { this.segments = segments; }
        public List<TranslatedScriptSegment> segments() { return segments; }
    }
}
