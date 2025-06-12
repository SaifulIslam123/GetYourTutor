package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.domain.entity.JobStatus;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
