package com.getyourtutor.controller;

import com.getyourtutor.dto.request.ApplicationStatusUpdateRequest;
import com.getyourtutor.dto.response.JobApplicationResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs/{jobId}/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<JobApplicationResponse> applyForJob(@PathVariable Long jobId, @AuthenticationPrincipal UserPrincipal currentUser) {
        JobApplicationResponse response = jobApplicationService.applyForJob(jobId, currentUser.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsForJob(@PathVariable Long jobId, @AuthenticationPrincipal UserPrincipal currentUser) {
        List<JobApplicationResponse> applications = jobApplicationService.getApplicationsForJob(jobId, currentUser.getUsername());
        return ResponseEntity.ok(applications);
    }

    @PutMapping("/{applicationId}")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobApplicationResponse> updateApplicationStatus(@PathVariable Long jobId, @PathVariable Long applicationId, @Valid @RequestBody ApplicationStatusUpdateRequest statusUpdateRequest, @AuthenticationPrincipal UserPrincipal currentUser) {
        JobApplicationResponse updatedApplication = jobApplicationService.updateApplicationStatus(jobId, applicationId, statusUpdateRequest, currentUser.getUsername());
        return ResponseEntity.ok(updatedApplication);
    }
}
