package com.clouddubber.application;

import static org.assertj.core.api.Assertions.*;

import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.port.Ports;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;

class DubbingServiceTest {
    @Test void createJob(){ var svc=svc(); var j=svc.createDubbingJob("a.mp4","video/mp4",1,new ByteArrayInputStream(new byte[]{1})); assertThat(j.status).isEqualTo(Enums.DubbingJobStatus.CREATED); }
    @Test void startPipeline(){ var s=svc(); DubbingJob j=s.createDubbingJob("a","video/mp4",1,new ByteArrayInputStream(new byte[]{1})); var u=s.startPipeline(j.id); assertThat(u.status).isEqualTo(Enums.DubbingJobStatus.AUDIO_EXTRACTION_PENDING); }
    @Test void updateAdaptation(){ var s=svc(); var seg=((InMemSeg)((Ports.DubbingSegmentRepository)repos[1])); seg.map.put("s1",new DubbingSegment("s1","j1",0,0.0,1.0,"orig",null,null,Enums.DubbingSegmentStatus.PENDING,null)); var up=s.updateAdaptation("j1","s1","abc"); assertThat(up.adaptedText).isEqualTo("abc"); }
    @Test void consentRequired(){ var s=svc(); assertThatThrownBy(()->s.createVoiceProfile("x",false)).isInstanceOf(IllegalArgumentException.class); }
    static Object[] repos;
    private DubbingService svc(){ var jobs=new InMemJob(); var seg=new InMemSeg(); var v=new InMemVoice(); repos=new Object[]{jobs,seg,v}; Ports.ObjectStorageGateway storage=new Ports.ObjectStorageGateway(){ public String upload(String k,java.io.InputStream i,long z,String c){return k;} public java.nio.file.Path downloadToTempFile(String k, java.nio.file.Path t){return t;} public Ports.AssetReference uploadFile(String k, java.nio.file.Path f, String c){return new Ports.AssetReference(k,c);} }; return new DubbingService(jobs,seg,v,storage,id->{},Instant::now,()->UUID.randomUUID().toString()); }
    static class InMemJob implements Ports.DubbingJobRepository { Map<String,DubbingJob> map=new HashMap<>(); public DubbingJob save(DubbingJob j){map.put(j.id,j);return j;} public Optional<DubbingJob> findById(String id){return Optional.ofNullable(map.get(id));} public List<DubbingJob> search(int p,int s){return new ArrayList<>(map.values());} public List<DubbingJob> findByStatus(Enums.DubbingJobStatus st,int limit){ return map.values().stream().filter(j->j.status==st).limit(limit).toList();}}
    static class InMemSeg implements Ports.DubbingSegmentRepository { Map<String,DubbingSegment> map=new HashMap<>(); public List<DubbingSegment> findByJobId(String j){return map.values().stream().filter(x->x.jobId.equals(j)).toList();} public Optional<DubbingSegment> findById(String id){return Optional.ofNullable(map.get(id));} public DubbingSegment save(DubbingSegment s){map.put(s.id,s);return s;} public boolean existsByJobIdAndSegmentIndex(String jobId,int segmentIndex){ return map.values().stream().anyMatch(x->x.jobId.equals(jobId) && x.segmentIndex==segmentIndex);} }
    static class InMemVoice implements Ports.VoiceProfileRepository { Map<String,com.clouddubber.domain.model.VoiceProfile> m=new HashMap<>(); public com.clouddubber.domain.model.VoiceProfile save(com.clouddubber.domain.model.VoiceProfile v){m.put(v.id(),v);return v;} public Optional<com.clouddubber.domain.model.VoiceProfile> findById(String i){return Optional.ofNullable(m.get(i));}}
}
