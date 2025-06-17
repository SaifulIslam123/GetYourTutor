package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Profile;
import com.getyourtutor.dto.request.ProfileRequest;
import com.getyourtutor.dto.response.ProfileResponse;

import java.util.Optional;
import java.util.UUID;

public interface ProfileService {
    Profile createProfile(UUID userId, String bio, String qualifications, String experience, String photoUrl);
    Optional<Profile> getProfileByUserId(UUID userId);
    boolean profileExistsForUser(UUID userId);
    ProfileResponse getProfileByUsername(String username);
    ProfileResponse updateProfile(String username, ProfileRequest request);
}
