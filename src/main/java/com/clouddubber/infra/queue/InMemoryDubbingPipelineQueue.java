package com.clouddubber.infra.queue;

import com.clouddubber.domain.port.Ports;
import org.springframework.stereotype.Component;

@Component
public class InMemoryDubbingPipelineQueue implements Ports.DubbingPipelineQueue {
    public void publishStart(String jobId) {}
}
