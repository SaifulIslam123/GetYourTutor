package com.getyourtutor.service.impl;

import com.getyourtutor.domain.JobStatus;
import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.JobPostingRequest;
import com.getyourtutor.dto.response.JobPostingResponse;
import com.getyourtutor.dto.response.JobPostingSummaryResponse;
import com.getyourtutor.dto.response.JobDetailResponse;
import com.getyourtutor.dto.response.JobSummaryResponse;
import com.getyourtutor.exception.BadRequestException;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public JobPostingResponse createJob(JobPostingRequest jobPostingRequest, String username) {
        User consumer = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));

        Job job = new Job();
        job.setTitle(jobPostingRequest.getTitle());
        job.setDescription(jobPostingRequest.getDescription());
        job.setSubject(jobPostingRequest.getSubject());
        job.setBudget(jobPostingRequest.getBudget());
        job.setDeadline(jobPostingRequest.getDeadline());
        job.setConsumer(consumer);
        job.setStatus(JobStatus.OPEN);

        Job savedJob = jobRepository.save(job);
        return mapToJobPostingResponse(savedJob);
    }

    @Override
    @Transactional(readOnly = true)
    public JobPostingResponse getJobById(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));
        return mapToJobPostingResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobPostingSummaryResponse> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable).map(this::mapToJobPostingSummaryResponse);
    }

    @Override
    @Transactional
    public JobPostingResponse updateJob(Long jobId, JobPostingRequest jobPostingRequest, String username) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to update this job posting.");
        }

        job.setTitle(jobPostingRequest.getTitle());
        job.setDescription(jobPostingRequest.getDescription());
        job.setSubject(jobPostingRequest.getSubject());
        job.setBudget(jobPostingRequest.getBudget());
        job.setDeadline(jobPostingRequest.getDeadline());

        Job updatedJob = jobRepository.save(job);
        return mapToJobPostingResponse(updatedJob);
    }

    @Override
    @Transactional
    public void deleteJob(Long jobId, String username) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to delete this job posting.");
        }

        jobRepository.delete(job);
    }

    private JobPostingResponse mapToJobPostingResponse(Job job) {
        return JobPostingResponse.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .description(job.getDescription())
                .subject(job.getSubject())
                .budget(job.getBudget())
                .deadline(job.getDeadline())
                .status(job.getStatus())
                .consumerUsername(job.getConsumer().getUsername())
                .createdAt(job.getCreatedAt())
                .build();
    }

    private JobPostingSummaryResponse mapToJobPostingSummaryResponse(Job job) {
        return JobPostingSummaryResponse.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .subject(job.getSubject())
                .budget(job.getBudget())
                .status(job.getStatus())
                .consumerUsername(job.getConsumer().getUsername())
                .createdAt(job.getCreatedAt())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobSummaryResponse> getMyActiveJobs(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return jobRepository.findByConsumerOrTutor(user, user, pageable).map(this::mapToJobSummaryResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public JobDetailResponse getActiveJobDetails(Long jobId, String username) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getConsumer().getUsername().equals(username) && (job.getTutor() == null || !job.getTutor().getUsername().equals(username))) {
            throw new AccessDeniedException("You are not authorized to view this job.");
        }

        return mapToJobDetailResponse(job);
    }

    @Override
    @Transactional
    public JobDetailResponse completeJob(Long jobId, String username) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (!job.getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("Only the consumer can mark this job as complete.");
        }

        job.setStatus(JobStatus.COMPLETED);
        Job completedJob = jobRepository.save(job);
        return mapToJobDetailResponse(completedJob);
    }

    private JobDetailResponse mapToJobDetailResponse(Job job) {
        return JobDetailResponse.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .description(job.getDescription())
                .subject(job.getSubject())
                .status(job.getStatus())
                .consumerUsername(job.getConsumer().getUsername())
                .tutorUsername(job.getTutor() != null ? job.getTutor().getUsername() : null)
                .startDate(job.getStartDate())
                .endDate(job.getEndDate())
                .hourlyRate(job.getHourlyRate())
                .createdAt(job.getCreatedAt())
                .build();
    }

    private JobSummaryResponse mapToJobSummaryResponse(Job job) {
        return JobSummaryResponse.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .subject(job.getSubject())
                .status(job.getStatus())
                .consumerUsername(job.getConsumer().getUsername())
                .tutorUsername(job.getTutor() != null ? job.getTutor().getUsername() : null)
                .createdAt(job.getCreatedAt())
                .build();
    }
}
