package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.JobApplication;
import com.getyourtutor.domain.entity.JobPosting;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.domain.entity.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobApplicationRepository extends BaseRepository<JobApplication, Long> {
    Page<JobApplication> findByJobPosting(JobPosting jobPosting, Pageable pageable);
    Page<JobApplication> findByTutor(User tutor, Pageable pageable);
    Page<JobApplication> findByJobPostingAndStatus(JobPosting jobPosting, ApplicationStatus status, Pageable pageable);
    boolean existsByJobPostingAndTutor(JobPosting jobPosting, User tutor);
    Optional<JobApplication> findByJobPostingAndTutor(JobPosting jobPosting, User tutor);
    long countByJobPostingAndStatus(JobPosting jobPosting, ApplicationStatus status);
}
