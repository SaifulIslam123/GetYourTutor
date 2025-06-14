package com.getyourtutor.controller;

import com.getyourtutor.dto.request.JobApplicationRequest;
import com.getyourtutor.dto.response.JobApplicationResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService jobApplicationService;

    @PostMapping("/postings/{jobId}/apply")
    @PreAuthorize("hasRole('TUTOR')")
    public ResponseEntity<JobApplicationResponse> applyForJob(@PathVariable Long jobId,
                                                              @Valid @RequestBody JobApplicationRequest applicationRequest,
                                                              @AuthenticationPrincipal UserPrincipal currentUser) {
        JobApplicationResponse response = jobApplicationService.applyToJob(jobId, currentUser.getUsername(), applicationRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/postings/{jobId}/applications")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<List<JobApplicationResponse>> getApplicationsForJob(@PathVariable Long jobId,
                                                                              @AuthenticationPrincipal UserPrincipal currentUser) {
        List<JobApplicationResponse> responses = jobApplicationService.getApplicationsForJob(jobId, currentUser.getUsername());
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/applications/{applicationId}/approve")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobApplicationResponse> approveApplication(@PathVariable Long applicationId,
                                                                     @AuthenticationPrincipal UserPrincipal currentUser) {
        JobApplicationResponse response = jobApplicationService.approveApplication(applicationId, currentUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/applications/{applicationId}/reject")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<JobApplicationResponse> rejectApplication(@PathVariable Long applicationId,
                                                                    @AuthenticationPrincipal UserPrincipal currentUser) {
        JobApplicationResponse response = jobApplicationService.rejectApplication(applicationId, currentUser.getUsername());
        return ResponseEntity.ok(response);
    }
}
