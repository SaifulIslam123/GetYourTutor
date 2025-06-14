package com.getyourtutor.service;

import com.getyourtutor.dto.request.JobPostingRequest;
import com.getyourtutor.dto.response.JobPostingResponse;
import com.getyourtutor.dto.response.JobPostingSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

package com.getyourtutor.service;

import com.getyourtutor.dto.request.JobPostingRequest;
import com.getyourtutor.dto.response.JobDetailResponse;
import com.getyourtutor.dto.response.JobPostingResponse;
import com.getyourtutor.dto.response.JobPostingSummaryResponse;
import com.getyourtutor.dto.response.JobSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {
    JobPostingResponse createJob(JobPostingRequest jobPostingRequest, String username);
    JobPostingResponse getJobById(Long jobId);
    Page<JobPostingSummaryResponse> getAllJobs(Pageable pageable);
    JobPostingResponse updateJob(Long jobId, JobPostingRequest jobPostingRequest, String username);
    void deleteJob(Long jobId, String username);

    // Methods for active jobs
    Page<JobSummaryResponse> getMyActiveJobs(String username, Pageable pageable);
    JobDetailResponse getActiveJobDetails(Long jobId, String username);
    JobDetailResponse completeJob(Long jobId, String username);
}
