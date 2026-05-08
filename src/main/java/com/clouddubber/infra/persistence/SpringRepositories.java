package com.clouddubber.infra.persistence;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringJobRepo extends JpaRepository<DubbingJobEntity, String> {
    List<DubbingJobEntity> findByStatusOrderByCreatedAtAsc(String status, Pageable pageable);
}

interface SpringSegRepo extends JpaRepository<DubbingSegmentEntity, String> {
    List<DubbingSegmentEntity> findByJobIdOrderBySegmentIndexAsc(String jobId);

    boolean existsByJobIdAndSegmentIndex(String jobId, Integer segmentIndex);
}

interface SpringVoiceRepo extends JpaRepository<VoiceProfileEntity, String> {}
