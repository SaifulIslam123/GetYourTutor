package com.getyourtutor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ReviewResponse {
    private Long reviewId;
    private int rating;
    private String comment;
    private UserResponse reviewer;
    private LocalDateTime createdAt;
}
