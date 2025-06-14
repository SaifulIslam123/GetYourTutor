package com.getyourtutor.service;

import com.getyourtutor.dto.request.ReviewRequest;
import com.getyourtutor.dto.response.ReviewResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    ReviewResponse createReview(ReviewRequest reviewRequest, String reviewerUsername);
    Page<ReviewResponse> getReviewsForUser(String username, Pageable pageable);
}
