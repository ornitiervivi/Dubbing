package com.clouddubber.infra.persistence;

import com.clouddubber.domain.model.Enums;
import com.clouddubber.domain.model.VoiceProfile;
import com.clouddubber.domain.port.Ports;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JpaVoiceProfileRepository implements Ports.VoiceProfileRepository {
    private final SpringVoiceRepo repo;

    JpaVoiceProfileRepository(SpringVoiceRepo repo) {
        this.repo = repo;
    }

    @Override
    public VoiceProfile save(VoiceProfile profile) {
        VoiceProfileEntity entity = repo.findById(profile.id()).orElse(new VoiceProfileEntity());
        entity.id = profile.id();
        entity.displayName = profile.displayName();
        entity.consentAccepted = profile.consentAccepted();
        entity.status = profile.status().name();
        entity.createdAt = profile.createdAt();
        VoiceProfileEntity entity = repo.findById(profile.id).orElse(new VoiceProfileEntity());
        entity.id = profile.id;
        entity.displayName = profile.displayName;
        entity.consentAccepted = profile.consentAccepted;
        entity.status = profile.status.name();
        entity.createdAt = profile.createdAt;
        repo.save(entity);
        return profile;
    }

    @Override
    public Optional<VoiceProfile> findById(String id) {
        return repo.findById(id).map(this::toDomain);
    }

    private VoiceProfile toDomain(VoiceProfileEntity entity) {
        return new VoiceProfile(
                entity.id,
                entity.displayName,
                entity.consentAccepted,
                Enums.VoiceProfileStatus.valueOf(entity.status),
                entity.createdAt
        );
    }
}
