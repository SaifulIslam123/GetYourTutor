package com.getyourtutor.controller;

import com.getyourtutor.dto.request.JobPostingRequest;
import com.getyourtutor.dto.response.JobPostingResponse;
import com.getyourtutor.dto.response.JobPostingSummaryResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.JobService;
import com.getyourtutor.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/postings")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobPostingResponse> createJobPosting(@Valid @RequestBody JobPostingRequest jobPostingRequest,
                                                               @AuthenticationPrincipal UserPrincipal currentUser) {
        JobPostingResponse jobPostingResponse = jobService.createJob(jobPostingRequest, currentUser.getUsername());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{jobId}")
                .buildAndExpand(jobPostingResponse.getJobId()).toUri();

        return ResponseEntity.created(location).body(jobPostingResponse);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobPostingResponse> getJobPostingById(@PathVariable Long jobId) {
        JobPostingResponse jobPostingResponse = jobService.getJobById(jobId);
        return ResponseEntity.ok(jobPostingResponse);
    }

    @GetMapping
    public Page<JobPostingSummaryResponse> getAllJobPostings(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        return jobService.getAllJobs(pageable);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobPostingResponse> updateJobPosting(@PathVariable Long jobId,
                                                               @Valid @RequestBody JobPostingRequest jobPostingRequest,
                                                               @AuthenticationPrincipal UserPrincipal currentUser) {
        JobPostingResponse updatedJob = jobService.updateJob(jobId, jobPostingRequest, currentUser.getUsername());
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<HttpStatus> deleteJobPosting(@PathVariable Long jobId,
                                                       @AuthenticationPrincipal UserPrincipal currentUser) {
        jobService.deleteJob(jobId, currentUser.getUsername());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
