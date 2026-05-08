package com.clouddubber.domain.model;

import java.time.Instant;

public class VoiceProfile {
    public String id;
    public String displayName;
    public boolean consentAccepted;
    public Enums.VoiceProfileStatus status;
    public Instant createdAt;

    public VoiceProfile(String id, String displayName, boolean consentAccepted, Enums.VoiceProfileStatus status, Instant createdAt) {
        if (!consentAccepted) {
            throw new IllegalArgumentException("Consent required");
        }
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name is required");
        }
        this.id = id;
        this.displayName = displayName;
        this.consentAccepted = consentAccepted;
        this.status = status;
        this.createdAt = createdAt;
    }
}
