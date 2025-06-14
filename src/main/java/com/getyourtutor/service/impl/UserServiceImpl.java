package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Role;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.UserRegistrationRequest;
import com.getyourtutor.repository.RoleRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegistrationRequest registrationRequest) throws Exception {
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new Exception("Username " + registrationRequest.getUsername() + " already exists");
        }

        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new Exception("Email " + registrationRequest.getEmail() + " already exists");
        }

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        Set<String> strRoles = registrationRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(roleName -> {
                Role role = roleRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found. " + roleName));
                roles.add(role);
            });
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
