package com.clouddubber.infra.config;

import com.clouddubber.application.DubbingService;
import com.clouddubber.domain.port.Ports;
import java.time.Instant;
import java.util.UUID;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean DubbingService dubbingService(Ports.DubbingJobRepository a, Ports.DubbingSegmentRepository b, Ports.VoiceProfileRepository c, Ports.ObjectStorageGateway d, Ports.DubbingPipelineQueue e) {
        return new DubbingService(a,b,c,d,e, Instant::now, () -> UUID.randomUUID().toString());
    }
}
