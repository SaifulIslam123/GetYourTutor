package com.getyourtutor.controller;

import com.getyourtutor.dto.request.ReviewRequest;
import com.getyourtutor.dto.response.ReviewResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.ReviewService;
import com.getyourtutor.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<ReviewResponse> createReview(@Valid @RequestBody ReviewRequest reviewRequest,
                                                       @AuthenticationPrincipal UserPrincipal currentUser) {
        ReviewResponse reviewResponse = reviewService.createReview(reviewRequest, currentUser.getUsername());
        return ResponseEntity.ok(reviewResponse);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsForUser(
            @PathVariable String username,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<ReviewResponse> reviews = reviewService.getReviewsForUser(username, pageable);
        return ResponseEntity.ok(reviews);
    }
}
