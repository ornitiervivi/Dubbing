package com.clouddubber.infra.persistence;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.port.Ports;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
class JpaDubbingJobRepository implements Ports.DubbingJobRepository {
    private final SpringJobRepo repo;

    JpaDubbingJobRepository(SpringJobRepo repo) {
        this.repo = repo;
    }

    @Override
    public DubbingJob save(DubbingJob job) {
        DubbingJobEntity entity = repo.findById(job.id).orElse(new DubbingJobEntity());
        entity.id = job.id;
        entity.originalFilename = job.originalFileName;
        entity.contentType = job.originalContentType;
        entity.sourceType = job.sourceType.name();
        entity.sourceAsset = job.sourceAsset;
        entity.extractedAudioAsset = job.extractedAudioAsset;
        entity.originalTranscriptAsset = job.originalTranscriptAsset;
        entity.translatedScriptAsset = job.translatedScriptAsset;
        entity.dubbedAudioAsset = job.dubbedAudioAsset;
        entity.renderedVideoAsset = job.renderedVideoAsset;
        entity.failureReason = job.failureReason;
        entity.status = job.status.name();
        entity.createdAt = job.createdAt;
        entity.updatedAt = job.updatedAt;
        repo.save(entity);
        return job;
    }

    @Override
    public Optional<DubbingJob> findById(String id) {
        return repo.findById(id).map(this::toDomain);
    }

    @Override
    public List<DubbingJob> search(int page, int size) {
        return repo.findAll(PageRequest.of(page, size)).stream().map(this::toDomain).toList();
    }

    @Override
    public List<DubbingJob> findByStatus(Enums.DubbingJobStatus status, int limit) {
        return repo.findByStatusOrderByCreatedAtAsc(status.name(), PageRequest.of(0, limit)).stream().map(this::toDomain).toList();
    }

    private DubbingJob toDomain(DubbingJobEntity entity) {
        return new DubbingJob(
                entity.id,
                entity.originalFilename,
                entity.contentType,
                Enums.DubbingSourceType.valueOf(entity.sourceType),
                Enums.DubbingJobStatus.valueOf(entity.status),
                entity.sourceAsset,
                entity.extractedAudioAsset,
                entity.originalTranscriptAsset,
                entity.translatedScriptAsset,
                entity.dubbedAudioAsset,
                entity.renderedVideoAsset,
                entity.failureReason,
                entity.createdAt,
                entity.updatedAt
        );
    }
}
