package com.clouddubber.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "dubbing_segments")
class DubbingSegmentEntity {
    @Id
    String id;
    String jobId;
    Integer segmentIndex;
    Double startTime;
    Double endTime;
    String originalText;
    String translatedText;
    String adaptedText;
    String status;
    String failureReason;
}
