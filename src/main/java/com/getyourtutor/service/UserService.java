package com.getyourtutor.service;

import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.UserRegistrationRequest;

import java.util.Optional;

public interface UserService {
    User registerUser(UserRegistrationRequest registrationRequest) throws Exception;

    Optional<User> findByUsername(String username);
}
