package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.domain.entity.JobStatus;
import com.getyourtutor.dto.request.JobRequest;
import com.getyourtutor.dto.response.JobResponse;
import com.getyourtutor.dto.response.JobSummaryResponse;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job createJobFromApplication(JobApplication application) {
        Job job = new Job();
        job.setJobApplication(application);
        job.setJobPosting(application.getJobPosting());
        job.setTutor(application.getTutor());
        job.setConsumer(application.getJobPosting().getCreatedBy());
        job.setStartDate(application.getJobPosting().getStartDate());
        job.setEndDate(application.getJobPosting().getEndDate());
        job.setHourlyRate(application.getProposedRate());
        job.setStatus(JobStatus.ACTIVE);

        return jobRepository.save(job);
    }

    @Override
    public JobResponse createJob(JobRequest request, String consumerUsername) {
        // TODO: implement properly
        return JobResponse.builder()
                .jobId(0L)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(JobStatus.ACTIVE)
                .build();
    }

    @Override
    public JobResponse getJobById(Long jobId) {
        // TODO: implement properly
        return JobResponse.builder().jobId(jobId).build();
    }

    @Override
    public Page<JobSummaryResponse> getAllJobs(Pageable pageable) {
        // TODO: implement properly
        return new PageImpl<>(java.util.Collections.emptyList(), pageable, 0);
    }

    @Override
    public JobResponse updateJob(Long jobId, JobRequest request, String consumerUsername) {
        // TODO: implement properly
        return JobResponse.builder().jobId(jobId).title(request.getTitle()).description(request.getDescription()).build();
    }

    @Override
    public void deleteJob(Long jobId, String consumerUsername) {
        // TODO: implement properly
    }
}
