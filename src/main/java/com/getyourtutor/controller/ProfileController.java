package com.getyourtutor.controller;

import com.getyourtutor.dto.request.ProfileRequest;
import com.getyourtutor.dto.response.ProfileResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        ProfileResponse profileResponse = profileService.getProfileByUsername(currentUser.getUsername());
        return ResponseEntity.ok(profileResponse);
    }

    @PutMapping("/me")
    public ResponseEntity<ProfileResponse> updateCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser, @Valid @RequestBody ProfileRequest profileRequest) {
        ProfileResponse profileResponse = profileService.updateProfile(currentUser.getUsername(), profileRequest);
        return ResponseEntity.ok(profileResponse);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ProfileResponse> getUserProfile(@PathVariable(value = "username") String username) {
        ProfileResponse profileResponse = profileService.getProfileByUsername(username);
        return ResponseEntity.ok(profileResponse);
    }
}
