package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.Review;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.repository.ReviewRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Review createReview(Long jobId, String reviewerUsername, int rating, String comment) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new Exception("Job not found with id: " + jobId));

        User reviewer = userRepository.findByUsername(reviewerUsername)
                .orElseThrow(() -> new Exception("User not found with username: " + reviewerUsername));

        if (reviewRepository.existsByJobAndReviewer(job, reviewer)) {
            throw new Exception("You have already submitted a review for this job.");
        }

        User reviewedUser;
        if (job.getConsumer().equals(reviewer)) {
            reviewedUser = job.getTutor();
        } else if (job.getTutor().equals(reviewer)) {
            reviewedUser = job.getConsumer();
        } else {
            throw new Exception("Reviewer is not associated with this job.");
        }

        Review review = new Review();
        review.setJob(job);
        review.setReviewer(reviewer);
        review.setReviewedUser(reviewedUser);
        review.setRating(rating);
        review.setComment(comment);

        return reviewRepository.save(review);
    }
}
