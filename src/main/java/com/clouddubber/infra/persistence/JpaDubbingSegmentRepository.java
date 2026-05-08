package com.clouddubber.infra.persistence;

import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.port.Ports;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JpaDubbingSegmentRepository implements Ports.DubbingSegmentRepository {
    private final SpringSegRepo repo;

    JpaDubbingSegmentRepository(SpringSegRepo repo) {
        this.repo = repo;
    }

    @Override
    public List<DubbingSegment> findByJobId(String jobId) {
        return repo.findByJobIdOrderBySegmentIndexAsc(jobId).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<DubbingSegment> findById(String segmentId) {
        return repo.findById(segmentId).map(this::toDomain);
    }

    @Override
    public DubbingSegment save(DubbingSegment segment) {
        DubbingSegmentEntity entity = repo.findById(segment.id).orElse(new DubbingSegmentEntity());
        entity.id = segment.id;
        entity.jobId = segment.jobId;
        entity.segmentIndex = segment.segmentIndex;
        entity.startTime = segment.startTime;
        entity.endTime = segment.endTime;
        entity.originalText = segment.originalText;
        entity.translatedText = segment.translatedText;
        entity.adaptedText = segment.adaptedText;
        entity.status = segment.status.name();
        entity.failureReason = segment.failureReason;
        repo.save(entity);
        return segment;
    }

    @Override
    public boolean existsByJobIdAndSegmentIndex(String jobId, int segmentIndex) {
        return repo.existsByJobIdAndSegmentIndex(jobId, segmentIndex);
    }

    private DubbingSegment toDomain(DubbingSegmentEntity entity) {
        return new DubbingSegment(
                entity.id,
                entity.jobId,
                entity.segmentIndex,
                entity.startTime,
                entity.endTime,
                entity.originalText,
                entity.translatedText,
                entity.adaptedText,
                Enums.DubbingSegmentStatus.valueOf(entity.status),
                entity.failureReason
        );
    }
}
