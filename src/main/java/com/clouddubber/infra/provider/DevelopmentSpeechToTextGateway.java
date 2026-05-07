package com.clouddubber.infra.provider;

import com.clouddubber.domain.model.SpeechModels;
import com.clouddubber.domain.port.Ports;
import java.util.List;

public class DevelopmentSpeechToTextGateway implements Ports.SpeechToTextGateway {
    public SpeechModels.SpeechToTextResult transcribe(SpeechModels.SpeechToTextRequest request) {
        return new SpeechModels.SpeechToTextResult(request.languageHint() == null ? "en" : request.languageHint(), 4.0, List.of(
                new SpeechModels.TranscribedSegment(0, 0.0, 2.0, "Development transcript segment 1"),
                new SpeechModels.TranscribedSegment(1, 2.0, 4.0, "Development transcript segment 2")
        ));
    }
}
