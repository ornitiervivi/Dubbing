package com.clouddubber.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.TranslationModels;
import com.clouddubber.domain.port.Ports;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class TranslationPipelineServiceTest {
    @Test
    void translatesAndMovesToVoiceGenerationPending() {
        var jobs = new Jobs();
        var segs = new Segs();
        var job = new DubbingJob("j1", "f", "c", Enums.DubbingSourceType.UPLOAD, Enums.DubbingJobStatus.TRANSLATION_PENDING, "s", null, null, null, null, null, null, Instant.now(), Instant.now());
        jobs.save(job);
        segs.save(new DubbingSegment("s1", "j1", 0, 0.0, 1.0, "Black Mage uses DPS rotation", null, null, Enums.DubbingSegmentStatus.TRANSCRIBED, null));
        TranslationPipelineService svc = new TranslationPipelineService(jobs, segs, new Storage(), new com.clouddubber.infra.provider.DevelopmentScriptTranslationGateway(), Instant::now, "en", "pt-BR", List.of(new TranslationModels.PreservedTerm("Black Mage"), new TranslationModels.PreservedTerm("DPS")), true, "json");
        svc.translateDubbingJobScript(job);
        assertThat(jobs.map.get("j1").status).isEqualTo(Enums.DubbingJobStatus.VOICE_GENERATION_PENDING);
        assertThat(segs.map.get("s1").adaptedText).isNotBlank();
    }

    static class Jobs implements Ports.DubbingJobRepository { Map<String, DubbingJob> map = new HashMap<>(); public DubbingJob save(DubbingJob job){map.put(job.id,job);return job;} public Optional<DubbingJob> findById(String id){return Optional.ofNullable(map.get(id));} public List<DubbingJob> search(int page,int size){return List.of();} public List<DubbingJob> findByStatus(Enums.DubbingJobStatus status,int limit){return map.values().stream().filter(x->x.status==status).limit(limit).toList();}}
    static class Segs implements Ports.DubbingSegmentRepository { Map<String,DubbingSegment> map=new HashMap<>(); public List<DubbingSegment> findByJobId(String jobId){return map.values().stream().filter(x->x.jobId.equals(jobId)).sorted(java.util.Comparator.comparingInt(x->x.segmentIndex)).toList();} public Optional<DubbingSegment> findById(String segmentId){return Optional.ofNullable(map.get(segmentId));} public DubbingSegment save(DubbingSegment s){map.put(s.id,s);return s;} public boolean existsByJobIdAndSegmentIndex(String jobId,int segmentIndex){return false;}}
    static class Storage implements Ports.ObjectStorageGateway { public String upload(String key, java.io.InputStream in, long size, String contentType){ return key; } public java.nio.file.Path downloadToTempFile(String key, java.nio.file.Path tempDirectory){ return tempDirectory; } public Ports.AssetReference uploadFile(String key, java.nio.file.Path file, String contentType){ return new Ports.AssetReference(key, contentType); }}
}
