package com.clouddubber.domain.model;

import java.util.List;

public final class TranslationModels {
    private TranslationModels() {}

    public record PreservedTerm(String value) {}
    public record ScriptSegmentToTranslate(String segmentId, int segmentIndex, double startTime, double endTime, String originalText) {}
    public record ScriptTranslationRequest(String jobId, String sourceLanguage, String targetLanguage, List<PreservedTerm> preservedTerms, List<ScriptSegmentToTranslate> segments) {}
    public record TranslatedScriptSegment(String segmentId, int segmentIndex, String originalText, String translatedText, String adaptedText, List<String> preservedTermsUsed, Double estimatedReadingDuration, List<String> warnings) {}
    public record ScriptTranslationResult(List<TranslatedScriptSegment> segments) {}
}
