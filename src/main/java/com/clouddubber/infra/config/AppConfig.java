package com.clouddubber.infra.config;

import com.clouddubber.application.AudioExtractionPipelineService;
import com.clouddubber.application.DubbingService;
import com.clouddubber.domain.port.Ports;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean DubbingService dubbingService(Ports.DubbingJobRepository a, Ports.DubbingSegmentRepository b, Ports.VoiceProfileRepository c, Ports.ObjectStorageGateway d, Ports.DubbingPipelineQueue e) {
        return new DubbingService(a,b,c,d,e, Instant::now, () -> UUID.randomUUID().toString());
    }

    @Bean AudioExtractionPipelineService audioExtractionPipelineService(Ports.DubbingJobRepository jobs, Ports.ObjectStorageGateway storage, Ports.AudioExtractionGateway extractionGateway, @Value("${clouddubber.ffmpeg.audio-format:mp3}") String format, @Value("${clouddubber.ffmpeg.audio-codec:libmp3lame}") String codec, @Value("${clouddubber.ffmpeg.timeout:PT30S}") Duration timeout, @Value("${clouddubber.pipeline.temp-directory:/tmp/clouddubber}") String tempDirectory) {
        return new AudioExtractionPipelineService(jobs, storage, extractionGateway, Instant::now, format, codec, timeout, Path.of(tempDirectory));
    }
}
