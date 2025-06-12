package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Review;

public interface ReviewService {
    Review createReview(Long jobId, String reviewerUsername, int rating, String comment) throws Exception;
}
