package com.getyourtutor.service.impl;

import com.getyourtutor.domain.JobStatus;
import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.Review;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.ReviewRequest;
import com.getyourtutor.dto.response.ReviewResponse;
import com.getyourtutor.exception.BadRequestException;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.repository.ReviewRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ReviewResponse createReview(ReviewRequest reviewRequest, String reviewerUsername) {
        Job job = jobRepository.findById(reviewRequest.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + reviewRequest.getJobId()));

        User reviewer = userRepository.findByUsername(reviewerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + reviewerUsername));

        if (!job.getConsumer().equals(reviewer)) {
            throw new AccessDeniedException("You are not authorized to review this job.");
        }

        if (job.getStatus() != JobStatus.COMPLETED) {
            throw new BadRequestException("Reviews can only be submitted for completed jobs.");
        }

        if (reviewRepository.existsByJobAndReviewer(job, reviewer)) {
            throw new BadRequestException("You have already submitted a review for this job.");
        }

        User reviewedUser = job.getTutor();

        Review review = new Review();
        review.setJob(job);
        review.setReviewer(reviewer);
        review.setReviewedUser(reviewedUser);
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());

        Review savedReview = reviewRepository.save(review);
        return mapToReviewResponse(savedReview);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsForUser(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return reviewRepository.findByReviewedUser(user, pageable).map(this::mapToReviewResponse);
    }

    private ReviewResponse mapToReviewResponse(Review review) {
        return ReviewResponse.builder()
                .reviewId(review.getReviewId())
                .jobId(review.getJob().getJobId())
                .reviewerUsername(review.getReviewer().getUsername())
                .reviewedUsername(review.getReviewedUser().getUsername())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}
