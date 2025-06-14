package com.getyourtutor.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequest {
    private String firstName;
    private String lastName;
    private String headline;
    private String bio;
    private String profilePictureUrl;
}
