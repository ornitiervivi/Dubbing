package com.clouddubber.application;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.VoiceProfile;
import com.clouddubber.domain.port.Ports;
import java.io.InputStream;
import java.util.List;

public class DubbingService {
    private final Ports.DubbingJobRepository jobs;
    private final Ports.DubbingSegmentRepository segments;
    private final Ports.VoiceProfileRepository voices;
    private final Ports.ObjectStorageGateway storage;
    private final Ports.DubbingPipelineQueue queue;
    private final Ports.ClockGateway clock;
    private final Ports.IdGeneratorGateway ids;

    public DubbingService(
            Ports.DubbingJobRepository jobs,
            Ports.DubbingSegmentRepository segments,
            Ports.VoiceProfileRepository voices,
            Ports.ObjectStorageGateway storage,
            Ports.DubbingPipelineQueue queue,
            Ports.ClockGateway clock,
            Ports.IdGeneratorGateway ids
    ) {
        this.jobs = jobs;
        this.segments = segments;
        this.voices = voices;
        this.storage = storage;
        this.queue = queue;
        this.clock = clock;
        this.ids = ids;
    }

    public DubbingJob createDubbingJob(String filename, String contentType, long size, InputStream file) {
        String id = ids.nextId();
        String key = storage.upload("jobs/" + id + "/original", file, size, contentType);
        DubbingJob job = new DubbingJob(
                id,
                filename,
                contentType,
                Enums.DubbingSourceType.UPLOAD,
                Enums.DubbingJobStatus.CREATED,
                key,
                null,
                null,
                null,
                null,
                null,
                null,
                clock.now(),
                clock.now()
        );
        return jobs.save(job);
    }

    public DubbingJob getById(String id) {
        return jobs.findById(id).orElseThrow();
    }

    public List<DubbingJob> search(int page, int size) {
        return jobs.search(page, size);
    }

    public List<DubbingSegment> getSegments(String jobId) {
        return segments.findByJobId(jobId);
    }

    public DubbingSegment updateAdaptation(String jobId, String segmentId, String adaptedText) {
        DubbingSegment segment = segments.findById(segmentId).orElseThrow();
        if (!segment.belongsToJob(jobId)) {
            throw new IllegalArgumentException("Segment does not belong to job");
        }
        segment.adapt(adaptedText);
        return segments.save(segment);
    }

    public DubbingJob startPipeline(String jobId) {
        DubbingJob job = getById(jobId);
        job.markAudioExtractionPending();
        DubbingJob saved = jobs.save(job);
        queue.publishStart(jobId);
        return saved;
    }

    public VoiceProfile createVoiceProfile(String name, boolean consentAccepted) {
        VoiceProfile profile = new VoiceProfile(ids.nextId(), name, consentAccepted, Enums.VoiceProfileStatus.ACTIVE, clock.now());
        return voices.save(profile);
    }

    public VoiceProfile getVoiceProfileById(String id) {
        return voices.findById(id).orElseThrow();
    }
}
