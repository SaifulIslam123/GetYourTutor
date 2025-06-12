package com.getyourtutor.service;

import com.getyourtutor.domain.entity.JobPosting;

public interface JobPostingService {
    JobPosting createJobPosting(JobPosting jobPosting, String username) throws Exception;
}
