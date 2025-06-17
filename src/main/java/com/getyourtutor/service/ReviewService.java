package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Review;
import com.getyourtutor.dto.request.ReviewRequest;
import com.getyourtutor.dto.response.ReviewResponse;
import java.util.List;

public interface ReviewService {
    Review createReview(Long jobId, String reviewerUsername, int rating, String comment) throws Exception;
    ReviewResponse createReview(ReviewRequest request, String reviewerUsername);

    List<ReviewResponse> getReviewsForJob(Long jobId);

    List<ReviewResponse> getReviewsForTutor(Long tutorId);
}
