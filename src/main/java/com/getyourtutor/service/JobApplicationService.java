package com.getyourtutor.service;

import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.dto.request.ApplicationStatusUpdateRequest;
import com.getyourtutor.dto.response.JobApplicationResponse;
import java.util.List;

public interface JobApplicationService {
    JobApplication applyToJob(Long jobPostingId, String username, JobApplication application) throws Exception;

    JobApplication approveApplication(Long applicationId) throws Exception;

    JobApplication rejectApplication(Long applicationId) throws Exception;

    JobApplicationResponse applyForJob(Long jobId, String tutorUsername);

    List<JobApplicationResponse> getApplicationsForJob(Long jobId, String consumerUsername);

    JobApplicationResponse updateApplicationStatus(Long jobId, Long applicationId, ApplicationStatusUpdateRequest request, String consumerUsername);
}
