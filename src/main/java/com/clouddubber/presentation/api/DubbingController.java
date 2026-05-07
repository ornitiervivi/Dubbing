package com.clouddubber.presentation.api;

import com.clouddubber.application.DubbingService;
import com.clouddubber.domain.model.DubbingJob;
import com.clouddubber.domain.model.DubbingSegment;
import com.clouddubber.domain.model.VoiceProfile;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
public class DubbingController {
    private final DubbingService service;
    public DubbingController(DubbingService service){this.service=service;}
    @PostMapping(value = "/dubbing-jobs", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DubbingJob create(@RequestPart("file") MultipartFile file) throws Exception { if(file.isEmpty()) throw new IllegalArgumentException("file empty"); return service.createDubbingJob(file.getOriginalFilename(), file.getContentType(), file.getSize(), file.getInputStream()); }
    @GetMapping("/dubbing-jobs/{jobId}") public DubbingJob get(@PathVariable String jobId){ return service.getById(jobId); }
    @GetMapping("/dubbing-jobs") public List<DubbingJob> list(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size){ return service.search(page,size); }
    @PostMapping("/dubbing-jobs/{jobId}/start") public DubbingJob start(@PathVariable String jobId){ return service.startPipeline(jobId); }
    @GetMapping("/dubbing-jobs/{jobId}/segments") public List<DubbingSegment> segments(@PathVariable String jobId){ return service.getSegments(jobId); }
    @PatchMapping("/dubbing-jobs/{jobId}/segments/{segmentId}/adaptation") public DubbingSegment adapt(@PathVariable String jobId, @PathVariable String segmentId, @RequestParam @NotBlank String adaptedText){ return service.updateAdaptation(jobId,segmentId,adaptedText); }
    @PostMapping("/voice-profiles") public VoiceProfile createVoice(@RequestParam String displayName, @RequestParam boolean consentAccepted){ return service.createVoiceProfile(displayName,consentAccepted); }
    @GetMapping("/voice-profiles/{voiceProfileId}") public VoiceProfile getVoice(@PathVariable String voiceProfileId){ return service.getVoiceProfileById(voiceProfileId); }
}
