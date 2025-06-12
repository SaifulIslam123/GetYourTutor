package com.getyourtutor.service;

import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.UserProfileDto;

import java.util.Optional;

public interface UserService {
    User registerNewUser(User user) throws Exception;

    Optional<User> findByUsername(String username);
    UserProfileDto getUserProfile(String username) throws Exception;
}
