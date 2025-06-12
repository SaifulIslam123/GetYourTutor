package com.getyourtutor.service;

import com.getyourtutor.domain.entity.JobApplication;

public interface JobApplicationService {
    JobApplication applyToJob(Long jobPostingId, String username, JobApplication application) throws Exception;

    JobApplication approveApplication(Long applicationId) throws Exception;

    JobApplication rejectApplication(Long applicationId) throws Exception;
}
