package com.clouddubber.infra.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "voice_profiles")
class VoiceProfileEntity {
    @Id
    String id;
    String displayName;
    boolean consentAccepted;
    String status;
    Instant createdAt;
}
