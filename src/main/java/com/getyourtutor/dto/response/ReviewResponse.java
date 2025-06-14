package com.getyourtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private Long reviewId;
    private Long jobId;
    private String reviewerUsername;
    private String reviewedUsername;
    private Integer rating;
    private String comment;
    private Date createdAt;
}
