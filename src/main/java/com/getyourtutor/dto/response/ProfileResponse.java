package com.getyourtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProfileResponse {
    private UUID userId;
    private String username;
    private String firstName;
    private String lastName;
    private String headline;
    private String bio;
    private String profilePictureUrl;
}
