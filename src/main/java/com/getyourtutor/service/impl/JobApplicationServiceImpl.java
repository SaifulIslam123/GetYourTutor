package com.getyourtutor.service.impl;

import com.getyourtutor.domain.ApplicationStatus;
import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.JobApplicationRequest;
import com.getyourtutor.dto.response.JobApplicationResponse;
import com.getyourtutor.exception.BadRequestException;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.JobApplicationRepository;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.JobApplicationService;
import com.getyourtutor.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final JobService jobService;

    @Override
    @Transactional
    public JobApplicationResponse applyToJob(Long jobId, String username, JobApplicationRequest applicationRequest) {
        User tutor = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (jobApplicationRepository.existsByJobAndTutor(job, tutor)) {
            throw new BadRequestException("You have already applied for this job.");
        }

        JobApplication application = new JobApplication();
        application.setJob(job);
        application.setTutor(tutor);
        application.setProposedRate(applicationRequest.getProposedRate());
        application.setCoverLetter(applicationRequest.getCoverLetter());
        application.setStatus(ApplicationStatus.PENDING);

        JobApplication savedApplication = jobApplicationRepository.save(application);
        return mapToJobApplicationResponse(savedApplication);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobApplicationResponse> getApplicationsForJob(Long jobId, String username) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to view applications for this job.");
        }

        return jobApplicationRepository.findByJob_JobId(jobId).stream()
                .map(this::mapToJobApplicationResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public JobApplicationResponse approveApplication(Long applicationId, String username) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + applicationId));

        Job job = application.getJob();

        if (!job.getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to approve this application.");
        }

        // Update Job to be an active job
        job.setTutor(application.getTutor());
        job.setHourlyRate(application.getProposedRate());
        job.setStatus(com.getyourtutor.domain.JobStatus.ACTIVE);
        jobRepository.save(job);

        // Set application status to accepted
        application.setStatus(ApplicationStatus.ACCEPTED);
        JobApplication savedApplication = jobApplicationRepository.save(application);

        // Reject all other pending applications for this job
        List<JobApplication> otherApplications = jobApplicationRepository
                .findByJobAndStatusAndJobApplicationIdNot(job, ApplicationStatus.PENDING, applicationId);

        otherApplications.forEach(otherApp -> otherApp.setStatus(ApplicationStatus.REJECTED));
        jobApplicationRepository.saveAll(otherApplications);

        return mapToJobApplicationResponse(savedApplication);
    }

    @Override
    @Transactional
    public JobApplicationResponse rejectApplication(Long applicationId, String username) {
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Job application not found with id: " + applicationId));

        if (!application.getJob().getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to reject this application.");
        }

        application.setStatus(ApplicationStatus.REJECTED);
        JobApplication savedApplication = jobApplicationRepository.save(application);
        return mapToJobApplicationResponse(savedApplication);
    }

    private JobApplicationResponse mapToJobApplicationResponse(JobApplication application) {
        return JobApplicationResponse.builder()
                .jobApplicationId(application.getJobApplicationId())
                .jobId(application.getJob().getJobId())
                .tutorUsername(application.getTutor().getUsername())
                .proposedRate(application.getProposedRate())
                .coverLetter(application.getCoverLetter())
                .status(application.getStatus())
                .appliedAt(application.getCreatedAt())
                .build();
    }
}
