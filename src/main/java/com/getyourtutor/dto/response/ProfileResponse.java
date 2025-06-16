package com.getyourtutor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProfileResponse {
    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private String headline;
    private String bio;
}
