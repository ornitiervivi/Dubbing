package com.clouddubber.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.VoiceProfile;
import com.clouddubber.domain.port.Ports;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DubbingServiceTest {
    private static Object[] repos;

    @Test
    void createJob() {
        DubbingService service = svc();
        DubbingJob job = service.createDubbingJob("a.mp4", "video/mp4", 1, new ByteArrayInputStream(new byte[]{1}));
        assertThat(job.status).isEqualTo(Enums.DubbingJobStatus.CREATED);
    }

    @Test
    void createJobShouldRejectInvalidInput() {
        DubbingService service = svc();
        assertThatThrownBy(() -> service.createDubbingJob("", "video/mp4", 1, new ByteArrayInputStream(new byte[]{1})))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.createDubbingJob("a.mp4", "", 1, new ByteArrayInputStream(new byte[]{1})))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.createDubbingJob("a.mp4", "video/mp4", 0, new ByteArrayInputStream(new byte[]{1})))
                .isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.createDubbingJob("a.mp4", "video/mp4", 1, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void startPipeline() {
        DubbingService service = svc();
        DubbingJob job = service.createDubbingJob("a", "video/mp4", 1, new ByteArrayInputStream(new byte[]{1}));
        DubbingJob updated = service.startPipeline(job.id);
        assertThat(updated.status).isEqualTo(Enums.DubbingJobStatus.AUDIO_EXTRACTION_PENDING);
    }

    @Test
    void updateAdaptation() {
        DubbingService service = svc();
        InMemSeg seg = (InMemSeg) repos[1];
        seg.map.put("s1", new DubbingSegment("s1", "j1", 0, 0.0, 1.0, "orig", null, null, Enums.DubbingSegmentStatus.PENDING, null));
        DubbingSegment updated = service.updateAdaptation("j1", "s1", "abc");
        assertThat(updated.adaptedText).isEqualTo("abc");
    }

    @Test
    void consentRequired() {
        DubbingService service = svc();
        assertThatThrownBy(() -> service.createVoiceProfile("x", false)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void searchShouldValidatePaging() {
        DubbingService service = svc();
        assertThatThrownBy(() -> service.search(-1, 10)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> service.search(0, 0)).isInstanceOf(IllegalArgumentException.class);
    }

    private DubbingService svc() {
        InMemJob jobs = new InMemJob();
        InMemSeg seg = new InMemSeg();
        InMemVoice voice = new InMemVoice();
        repos = new Object[]{jobs, seg, voice};

        Ports.ObjectStorageGateway storage = new Ports.ObjectStorageGateway() {
            @Override
            public String upload(String key, InputStream in, long size, String contentType) {
                return key;
            }

            @Override
            public Path downloadToTempFile(String key, Path tempDirectory) {
                return tempDirectory;
            }

            @Override
            public Ports.AssetReference uploadFile(String key, Path file, String contentType) {
                return new Ports.AssetReference(key, contentType);
            }
        };

        return new DubbingService(jobs, seg, voice, storage, id -> { }, Instant::now, () -> UUID.randomUUID().toString());
    }

    static class InMemJob implements Ports.DubbingJobRepository {
        private final Map<String, DubbingJob> map = new HashMap<>();

        @Override
        public DubbingJob save(DubbingJob job) {
            map.put(job.id, job);
            return job;
        }

        @Override
        public Optional<DubbingJob> findById(String id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public List<DubbingJob> search(int page, int size) {
            return new ArrayList<>(map.values());
        }

        @Override
        public List<DubbingJob> findByStatus(Enums.DubbingJobStatus status, int limit) {
            return map.values().stream().filter(job -> job.status == status).limit(limit).toList();
        }
    }

    static class InMemSeg implements Ports.DubbingSegmentRepository {
        private final Map<String, DubbingSegment> map = new HashMap<>();

        @Override
        public List<DubbingSegment> findByJobId(String jobId) {
            return map.values().stream().filter(segment -> segment.jobId.equals(jobId)).toList();
        }

        @Override
        public Optional<DubbingSegment> findById(String id) {
            return Optional.ofNullable(map.get(id));
        }

        @Override
        public DubbingSegment save(DubbingSegment segment) {
            map.put(segment.id, segment);
            return segment;
        }

        @Override
        public boolean existsByJobIdAndSegmentIndex(String jobId, int segmentIndex) {
            return map.values().stream().anyMatch(segment -> segment.jobId.equals(jobId) && segment.segmentIndex == segmentIndex);
        }
    }

    static class InMemVoice implements Ports.VoiceProfileRepository {
        private final Map<String, VoiceProfile> map = new HashMap<>();

        @Override
        public VoiceProfile save(VoiceProfile profile) {
            map.put(profile.id(), profile);
            return profile;
        }

        @Override
        public Optional<VoiceProfile> findById(String id) {
            return Optional.ofNullable(map.get(id));
        }
    }
}
