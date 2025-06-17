package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.dto.request.JobRequest;
import com.getyourtutor.dto.response.JobResponse;
import com.getyourtutor.dto.response.JobSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
    Job createJobFromApplication(JobApplication application);

    JobResponse createJob(JobRequest request, String consumerUsername);

    JobResponse getJobById(Long jobId);

    Page<JobSummaryResponse> getAllJobs(Pageable pageable);

    JobResponse updateJob(Long jobId, JobRequest request, String consumerUsername);

    void deleteJob(Long jobId, String consumerUsername);
}
