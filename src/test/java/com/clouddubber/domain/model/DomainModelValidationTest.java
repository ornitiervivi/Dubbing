package com.clouddubber.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class DomainModelValidationTest {
    @Test
    void dubbingJobShouldRejectInvalidTransition() {
        DubbingJob failedJob = new DubbingJob("id", "file.mp4", "video/mp4", Enums.DubbingSourceType.UPLOAD, Enums.DubbingJobStatus.FAILED, "asset", null, null, null, null, null, null, Instant.now(), Instant.now());
        assertThatThrownBy(failedJob::markAudioExtractionPending).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void dubbingSegmentShouldRejectBlankAdaptedText() {
        DubbingSegment segment = new DubbingSegment("s1", "j1", 0, 0.0, 1.0, "orig", null, null, Enums.DubbingSegmentStatus.PENDING, null);
        assertThatThrownBy(() -> segment.adapt(" ")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void voiceProfileShouldRequireConsentAndDisplayName() {
        assertThatThrownBy(() -> new VoiceProfile("id", "", true, Enums.VoiceProfileStatus.ACTIVE, Instant.now())).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> new VoiceProfile("id", "name", false, Enums.VoiceProfileStatus.ACTIVE, Instant.now())).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void voiceProfileShouldExposeAccessors() {
        VoiceProfile profile = new VoiceProfile("id", "name", true, Enums.VoiceProfileStatus.ACTIVE, Instant.EPOCH);
        assertThat(profile.id()).isEqualTo("id");
        assertThat(profile.displayName()).isEqualTo("name");
        assertThat(profile.consentAccepted()).isTrue();
        assertThat(profile.status()).isEqualTo(Enums.VoiceProfileStatus.ACTIVE);
        assertThat(profile.createdAt()).isEqualTo(Instant.EPOCH);
    }
}
