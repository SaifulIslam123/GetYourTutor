package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Profile;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.ProfileRequest;
import com.getyourtutor.dto.response.ProfileResponse;
import com.getyourtutor.repository.ProfileRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    @Transactional(readOnly = true)
    public ProfileResponse getProfileByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Profile profile = profileRepository.findByUser(user)
                .orElse(new Profile()); // Return empty profile if not found

        return new ProfileResponse(
                user.getUserId(),
                user.getUsername(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getHeadline(),
                profile.getBio(),
                profile.getPhotoUrl()
        );
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(String username, ProfileRequest profileRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));

        Profile profile = profileRepository.findByUser(user).orElseGet(() -> {
            Profile newProfile = new Profile();
            newProfile.setUser(user);
            return newProfile;
        });

        profile.setFirstName(profileRequest.getFirstName());
        profile.setLastName(profileRequest.getLastName());
        profile.setHeadline(profileRequest.getHeadline());
        profile.setBio(profileRequest.getBio());
        profile.setPhotoUrl(profileRequest.getProfilePictureUrl());

        Profile savedProfile = profileRepository.save(profile);

        return new ProfileResponse(
                user.getUserId(),
                user.getUsername(),
                savedProfile.getFirstName(),
                savedProfile.getLastName(),
                savedProfile.getHeadline(),
                savedProfile.getBio(),
                savedProfile.getPhotoUrl()
        );
    }
}
