package com.clouddubber.domain.model;

import java.time.Instant;

public class VoiceProfile {
    private final String id;
    private final String displayName;
    private final boolean consentAccepted;
    private final Enums.VoiceProfileStatus status;
    private final Instant createdAt;

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

    public String id() {
        return id;
    }

    public String displayName() {
        return displayName;
    }

    public boolean consentAccepted() {
        return consentAccepted;
    }

    public Enums.VoiceProfileStatus status() {
        return status;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isConsentAccepted() {
        return consentAccepted;
    }

    public Enums.VoiceProfileStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
