package com.getyourtutor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String username;
    private String firstName;
    private String lastName;
    private Double averageRating;
    private Long totalReviews;
}
