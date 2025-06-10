package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Profile;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.repository.ProfileRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Override
    @Transactional
    public Profile createProfile(UUID userId, String bio, String qualifications, String experience, String photoUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        if (profileRepository.existsByUserId(userId)) {
            throw new IllegalStateException("Profile already exists for user: " + userId);
        }

        Profile profile = new Profile();
        profile.setUser(user);
        profile.setBio(bio);
        profile.setQualifications(qualifications);
        profile.setExperience(experience);
        profile.setPhotoUrl(photoUrl);

        return profileRepository.save(profile);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Profile> getProfileByUserId(UUID userId) {
        return userRepository.findById(userId).flatMap(profileRepository::findByUser);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean profileExistsForUser(UUID userId) {
        return profileRepository.existsByUserId(userId);
    }
}
