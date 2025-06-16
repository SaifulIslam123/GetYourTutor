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
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final ProfileService profileService;

    @GetMapping("/me/profile")
    public ResponseEntity<ProfileResponse> getCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser) {
        ProfileResponse profile = profileService.getProfileByUsername(currentUser.getUsername());
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/me/profile")
    public ResponseEntity<ProfileResponse> updateCurrentUserProfile(@AuthenticationPrincipal UserPrincipal currentUser, @Valid @RequestBody ProfileRequest profileRequest) {
        ProfileResponse updatedProfile = profileService.updateProfile(currentUser.getUsername(), profileRequest);
        return ResponseEntity.ok(updatedProfile);
    }
}
