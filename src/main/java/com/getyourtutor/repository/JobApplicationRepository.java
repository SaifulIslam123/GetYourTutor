package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
import java.util.List;

import com.getyourtutor.domain.ApplicationStatus;
import com.getyourtutor.domain.entity.Job;

import java.util.List;

public interface JobApplicationRepository extends BaseRepository<JobApplication, Long> {
    List<JobApplication> findByJob_JobId(Long jobId);
    List<JobApplication> findByJobAndStatusAndJobApplicationIdNot(Job job, ApplicationStatus status, Long jobApplicationId);

    List<JobApplication> findByJob_JobId(Long jobId);
    Page<JobApplication> findByJobPosting(JobPosting jobPosting, Pageable pageable);
    Page<JobApplication> findByTutor(User tutor, Pageable pageable);
    Page<JobApplication> findByJobPostingAndStatus(JobPosting jobPosting, ApplicationStatus status, Pageable pageable);
    Optional<JobApplication> findByJobPostingAndTutor(JobPosting jobPosting, User tutor);
    boolean existsByJobPostingAndTutor(JobPosting jobPosting, User tutor);
    long countByJobPostingAndStatus(JobPosting jobPosting, ApplicationStatus status);
}
