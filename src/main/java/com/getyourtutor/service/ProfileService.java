package com.getyourtutor.service;

import com.getyourtutor.dto.request.ProfileRequest;
import com.getyourtutor.dto.response.ProfileResponse;

public interface ProfileService {
    ProfileResponse getProfileByUsername(String username);
    ProfileResponse updateProfile(String username, ProfileRequest profileRequest);
}
