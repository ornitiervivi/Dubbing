package com.clouddubber.infra.provider;

import com.clouddubber.domain.model.TranslationModels;
import com.clouddubber.domain.port.Ports;
import java.util.Locale;

public class DevelopmentScriptTranslationGateway implements Ports.ScriptTranslationGateway {
    @Override
    public TranslationModels.ScriptTranslationResult translate(TranslationModels.ScriptTranslationRequest request) {
        var preserved = request.preservedTerms().stream().map(TranslationModels.PreservedTerm::value).toList();
        return new TranslationModels.ScriptTranslationResult(request.segments().stream().map(s -> {
            String translated = "ptbr:" + s.originalText();
            for (String term : preserved) {
                if (s.originalText().toLowerCase(Locale.ROOT).contains(term.toLowerCase(Locale.ROOT))) translated = translated.replace(term.toLowerCase(Locale.ROOT), term).replace(term, term);
            }
            return new TranslationModels.TranslatedScriptSegment(s.segmentId(), s.segmentIndex(), s.originalText(), translated, "adapted:" + translated, preserved, null, java.util.List.of());
        }).toList());
    }
}
