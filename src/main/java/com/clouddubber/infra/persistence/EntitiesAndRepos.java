package com.clouddubber.infra.persistence;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.VoiceProfile;
import com.clouddubber.domain.port.Ports;
import jakarta.persistence.Entity;import jakarta.persistence.Id;import jakarta.persistence.Table;
import java.time.Instant;import java.util.List;import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Entity @Table(name="dubbing_jobs") class DubbingJobEntity { @Id public String id; public String originalFilename; public String contentType; public long sizeBytes; public String storageKey; public String status; public Instant createdAt; }
@Entity @Table(name="dubbing_segments") class DubbingSegmentEntity { @Id public String id; public String jobId; public String adaptedText; public String status; }
@Entity @Table(name="voice_profiles") class VoiceProfileEntity { @Id public String id; public String displayName; public boolean consentAccepted; public String status; public Instant createdAt; }

interface SpringJobRepo extends JpaRepository<DubbingJobEntity,String> {}
interface SpringSegRepo extends JpaRepository<DubbingSegmentEntity,String> { List<DubbingSegmentEntity> findByJobId(String jobId); }
interface SpringVoiceRepo extends JpaRepository<VoiceProfileEntity,String> {}

@Repository
class JpaDubbingJobRepository implements Ports.DubbingJobRepository {
    private final SpringJobRepo repo; JpaDubbingJobRepository(SpringJobRepo repo){this.repo=repo;}
    public DubbingJob save(DubbingJob j){ DubbingJobEntity e=new DubbingJobEntity(); e.id=j.id;e.originalFilename=j.originalFilename;e.contentType=j.contentType;e.sizeBytes=j.sizeBytes;e.storageKey=j.storageKey;e.status=j.status.name();e.createdAt=j.createdAt; repo.save(e); return j;}
    public Optional<DubbingJob> findById(String id){ return repo.findById(id).map(e->new DubbingJob(e.id,e.originalFilename,e.contentType,e.sizeBytes,e.storageKey, Enums.DubbingJobStatus.valueOf(e.status),e.createdAt)); }
    public List<DubbingJob> search(int page,int size){ return repo.findAll(PageRequest.of(page,size)).stream().map(e->new DubbingJob(e.id,e.originalFilename,e.contentType,e.sizeBytes,e.storageKey, Enums.DubbingJobStatus.valueOf(e.status),e.createdAt)).toList(); }
}
@Repository class JpaDubbingSegmentRepository implements Ports.DubbingSegmentRepository {
    private final SpringSegRepo repo; JpaDubbingSegmentRepository(SpringSegRepo repo){this.repo=repo;}
    public List<DubbingSegment> findByJobId(String jobId){ return repo.findByJobId(jobId).stream().map(e->new DubbingSegment(e.id,e.jobId,e.adaptedText,Enums.DubbingSegmentStatus.valueOf(e.status))).toList(); }
    public Optional<DubbingSegment> findById(String id){ return repo.findById(id).map(e->new DubbingSegment(e.id,e.jobId,e.adaptedText,Enums.DubbingSegmentStatus.valueOf(e.status))); }
    public DubbingSegment save(DubbingSegment s){ DubbingSegmentEntity e=repo.findById(s.id).orElse(new DubbingSegmentEntity()); e.id=s.id;e.jobId=s.jobId;e.adaptedText=s.adaptedText;e.status=s.status.name(); repo.save(e); return s; }
}
@Repository class JpaVoiceProfileRepository implements Ports.VoiceProfileRepository {
    private final SpringVoiceRepo repo; JpaVoiceProfileRepository(SpringVoiceRepo repo){this.repo=repo;}
    public VoiceProfile save(VoiceProfile v){ VoiceProfileEntity e=new VoiceProfileEntity(); e.id=v.id;e.displayName=v.displayName;e.consentAccepted=v.consentAccepted;e.status=v.status.name();e.createdAt=v.createdAt; repo.save(e); return v;}
    public Optional<VoiceProfile> findById(String id){ return repo.findById(id).map(e->new VoiceProfile(e.id,e.displayName,e.consentAccepted,Enums.VoiceProfileStatus.valueOf(e.status),e.createdAt)); }
}
