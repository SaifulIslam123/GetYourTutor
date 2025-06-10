package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Profile;

import java.util.Optional;
import java.util.UUID;

public interface ProfileService {
    Profile createProfile(UUID userId, String bio, String qualifications, String experience, String photoUrl);
    Optional<Profile> getProfileByUserId(UUID userId);
    boolean profileExistsForUser(UUID userId);
}
