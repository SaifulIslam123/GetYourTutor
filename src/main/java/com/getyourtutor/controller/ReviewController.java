package com.getyourtutor.controller;

import com.getyourtutor.dto.request.ReviewRequest;
import com.getyourtutor.dto.response.ReviewResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest, @AuthenticationPrincipal UserPrincipal currentUser) {
        ReviewResponse reviewResponse = reviewService.createReview(reviewRequest, currentUser.getUsername());
        return new ResponseEntity<>(reviewResponse, HttpStatus.CREATED);
    }

    @GetMapping("/job/{jobId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsForJob(@PathVariable Long jobId) {
        List<ReviewResponse> reviews = reviewService.getReviewsForJob(jobId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/tutor/{tutorId}")
    public ResponseEntity<List<ReviewResponse>> getReviewsForTutor(@PathVariable Long tutorId) {
        List<ReviewResponse> reviews = reviewService.getReviewsForTutor(tutorId);
        return ResponseEntity.ok(reviews);
    }
}
