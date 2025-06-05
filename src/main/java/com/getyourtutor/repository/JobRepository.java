package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.JobStatus;
import com.getyourtutor.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface JobRepository extends BaseRepository<Job, Long> {
    Page<Job> findByTutor(User tutor, Pageable pageable);
    Page<Job> findByConsumer(User consumer, Pageable pageable);
    Page<Job> findByTutorAndStatus(User tutor, JobStatus status, Pageable pageable);
    Page<Job> findByConsumerAndStatus(User consumer, JobStatus status, Pageable pageable);
    
    @Query("SELECT j FROM Job j WHERE " +
           "(j.tutor = :user OR j.consumer = :user) AND " +
           "j.status = :status AND " +
           "(:startDate IS NULL OR j.startDate >= :startDate) AND " +
           "(:endDate IS NULL OR j.endDate <= :endDate)")
    Page<Job> findJobsByUserAndStatusAndDateRange(
            @Param("user") User user,
            @Param("status") JobStatus status,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}
