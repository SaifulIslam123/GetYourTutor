package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.UserProfileDto;
import com.getyourtutor.dto.request.UserRegistrationRequest;
import com.getyourtutor.repository.ReviewRepository;
import com.getyourtutor.repository.RoleRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public User registerNewUser(User user) throws Exception {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new Exception("Username " + user.getUsername() + " already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email " + user.getEmail() + " already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        roleRepository.findByName("ROLE_USER").ifPresent(role -> {
            user.setRoles(Collections.singleton(role));
        });

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserProfileDto getUserProfile(String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found with username: " + username));

        Double averageRating = reviewRepository.calculateAverageRating(user);
        Long totalReviews = reviewRepository.countVisibleReviews(user);

        // Handle case where there are no reviews, so averageRating is null
        if (averageRating == null) {
            averageRating = 0.0;
        }

        return new UserProfileDto(
                user.getUsername(),
                user.getProfile().getFirstName(),
                user.getProfile().getLastName(),
                averageRating,
                totalReviews
        );
    }

    @Override
    public void registerUser(UserRegistrationRequest request) throws Exception {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        registerNewUser(user);
    }
}
