package com.getyourtutor.controller;

import com.getyourtutor.dto.response.JobDetailResponse;
import com.getyourtutor.dto.response.JobSummaryResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.JobService;
import com.getyourtutor.utils.AppConstants;
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
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping("/my-active")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('TUTOR')")
    public ResponseEntity<Page<JobSummaryResponse>> getMyActiveJobs(
            @AuthenticationPrincipal UserPrincipal currentUser,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<JobSummaryResponse> activeJobs = jobService.getMyActiveJobs(currentUser.getUsername(), pageable);
        return ResponseEntity.ok(activeJobs);
    }

    @GetMapping("/{jobId}")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('TUTOR')")
    public ResponseEntity<JobDetailResponse> getActiveJobDetails(@PathVariable Long jobId, @AuthenticationPrincipal UserPrincipal currentUser) {
        JobDetailResponse jobDetails = jobService.getActiveJobDetails(jobId, currentUser.getUsername());
        return ResponseEntity.ok(jobDetails);
    }

    @PutMapping("/{jobId}/complete")
    @PreAuthorize("hasRole('CONSUM_ER')")
    public ResponseEntity<JobDetailResponse> completeJob(@PathVariable Long jobId, @AuthenticationPrincipal UserPrincipal currentUser) {
        JobDetailResponse completedJob = jobService.completeJob(jobId, currentUser.getUsername());
        return ResponseEntity.ok(completedJob);
    }
}
