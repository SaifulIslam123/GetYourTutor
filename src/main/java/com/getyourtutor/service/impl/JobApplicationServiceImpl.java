package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.ApplicationStatus;
import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.domain.entity.JobPosting;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.repository.JobApplicationRepository;
import com.getyourtutor.repository.JobPostingRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.JobApplicationService;
import com.getyourtutor.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobPostingRepository jobPostingRepository;

    @Autowired
    private JobService jobService;

    @Override
    public JobApplication applyToJob(Long jobPostingId, String username, JobApplication application) throws Exception {
        User tutor = userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found with username: " + username));

        JobPosting jobPosting = jobPostingRepository.findById(jobPostingId)
                .orElseThrow(() -> new Exception("JobPosting not found with id: " + jobPostingId));

        if (jobApplicationRepository.existsByJobPostingAndTutor(jobPosting, tutor)) {
            throw new Exception("You have already applied to this job posting.");
        }

        application.setTutor(tutor);
        application.setJobPosting(jobPosting);
        application.setStatus(ApplicationStatus.PENDING);

        return jobApplicationRepository.save(application);
    }

    @Override
    public JobApplication approveApplication(Long applicationId) throws Exception {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception("JobApplication not found with id: " + applicationId));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new Exception("Only pending applications can be approved. Current status: " + application.getStatus());
        }

        application.setStatus(ApplicationStatus.ACCEPTED);
        jobService.createJobFromApplication(application);

        return jobApplicationRepository.save(application);
    }

    @Override
    public JobApplication rejectApplication(Long applicationId) throws Exception {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new Exception("JobApplication not found with id: " + applicationId));

        if (application.getStatus() != ApplicationStatus.PENDING) {
            throw new Exception("Only pending applications can be rejected. Current status: " + application.getStatus());
        }

        application.setStatus(ApplicationStatus.REJECTED);
        return jobApplicationRepository.save(application);
    }
}
