package com.getyourtutor.controller;

import com.getyourtutor.dto.request.JobRequest;
import com.getyourtutor.dto.response.JobResponse;
import com.getyourtutor.dto.response.JobSummaryResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @PostMapping
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobResponse> createJob(@Valid @RequestBody JobRequest jobRequest, @AuthenticationPrincipal UserPrincipal currentUser) {
        JobResponse jobResponse = jobService.createJob(jobRequest, currentUser.getUsername());
        return new ResponseEntity<>(jobResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobResponse> getJobById(@PathVariable Long jobId) {
        JobResponse jobResponse = jobService.getJobById(jobId);
        return ResponseEntity.ok(jobResponse);
    }

    @GetMapping
    public ResponseEntity<Page<JobSummaryResponse>> getAllJobs(Pageable pageable) {
        Page<JobSummaryResponse> jobs = jobService.getAllJobs(pageable);
        return ResponseEntity.ok(jobs);
    }

    @PutMapping("/{jobId}")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobResponse> updateJob(@PathVariable Long jobId, @Valid @RequestBody JobRequest jobRequest, @AuthenticationPrincipal UserPrincipal currentUser) {
        JobResponse updatedJob = jobService.updateJob(jobId, jobRequest, currentUser.getUsername());
        return ResponseEntity.ok(updatedJob);
    }

    @DeleteMapping("/{jobId}")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<?> deleteJob(@PathVariable Long jobId, @AuthenticationPrincipal UserPrincipal currentUser) {
        jobService.deleteJob(jobId, currentUser.getUsername());
        return ResponseEntity.ok().build();
    }
}
