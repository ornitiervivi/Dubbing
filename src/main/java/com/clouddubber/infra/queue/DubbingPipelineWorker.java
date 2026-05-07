package com.clouddubber.infra.queue;

import com.clouddubber.application.AudioExtractionPipelineService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "clouddubber.pipeline.worker.enabled", havingValue = "true")
public class DubbingPipelineWorker {
    private final AudioExtractionPipelineService pipeline;
    private final int batchSize;

    public DubbingPipelineWorker(AudioExtractionPipelineService pipeline, org.springframework.core.env.Environment env) {
        this.pipeline = pipeline;
        this.batchSize = Integer.parseInt(env.getProperty("clouddubber.pipeline.worker.batch-size", "5"));
    }

    @Scheduled(fixedDelayString = "${clouddubber.pipeline.worker.fixed-delay:5000}")
    public void processPending() { pipeline.processPendingAudioExtractions(batchSize); }
}
