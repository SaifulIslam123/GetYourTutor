package com.getyourtutor.service;

import com.getyourtutor.dto.request.JobApplicationRequest;
import com.getyourtutor.dto.response.JobApplicationResponse;

import java.util.List;

public interface JobApplicationService {
    JobApplicationResponse applyToJob(Long jobId, String username, JobApplicationRequest applicationRequest);
    List<JobApplicationResponse> getApplicationsForJob(Long jobId, String username);
    JobApplicationResponse approveApplication(Long applicationId, String username);
    JobApplicationResponse rejectApplication(Long applicationId, String username);
}
