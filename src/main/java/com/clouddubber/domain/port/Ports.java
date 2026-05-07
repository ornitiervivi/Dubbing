package com.clouddubber.domain.port;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.VoiceProfile;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public final class Ports {
    private Ports() {}
    public interface DubbingJobRepository { DubbingJob save(DubbingJob job); Optional<DubbingJob> findById(String id); List<DubbingJob> search(int page, int size); }
    public interface DubbingSegmentRepository { List<DubbingSegment> findByJobId(String jobId); Optional<DubbingSegment> findById(String segmentId); DubbingSegment save(DubbingSegment segment); }
    public interface VoiceProfileRepository { VoiceProfile save(VoiceProfile vp); Optional<VoiceProfile> findById(String id); }
    public interface ObjectStorageGateway { String upload(String key, InputStream in, long size, String contentType); }
    public interface DubbingPipelineQueue { void publishStart(String jobId); }
    public interface ClockGateway { Instant now(); }
    public interface IdGeneratorGateway { String nextId(); }
}
