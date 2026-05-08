package com.clouddubber.infra.queue;

import com.clouddubber.application.AudioExtractionPipelineService;
import com.clouddubber.application.TranscriptionPipelineService;
import com.clouddubber.application.TranslationPipelineService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "clouddubber.pipeline.worker.enabled", havingValue = "true")
public class DubbingPipelineWorker {
    private final AudioExtractionPipelineService extraction;
    private final TranscriptionPipelineService transcription;
    private final TranslationPipelineService translation;
    private final int batchSize;

    public DubbingPipelineWorker(
            AudioExtractionPipelineService extraction,
            TranscriptionPipelineService transcription,
            TranslationPipelineService translation,
            @Value("${clouddubber.pipeline.worker.batch-size:5}") int batchSize
    ) {
        this.extraction = extraction;
        this.transcription = transcription;
        this.translation = translation;
        this.batchSize = batchSize;
    }

    @Scheduled(fixedDelayString = "${clouddubber.pipeline.worker.fixed-delay:5000}")
    public void processPending() {
        extraction.processPendingAudioExtractions(batchSize);
        transcription.processPendingTranscriptions(batchSize);
        translation.processPendingTranslations(batchSize);
    }
}
