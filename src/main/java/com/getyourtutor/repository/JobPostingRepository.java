package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.JobPosting;
import com.getyourtutor.domain.entity.JobPostingStatus;
import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobPostingRepository extends BaseRepository<JobPosting, Long> {
    Page<JobPosting> findByStatus(JobPostingStatus status, Pageable pageable);
    Page<JobPosting> findByCreatedByAndStatus(User createdBy, JobPostingStatus status, Pageable pageable);
    Page<JobPosting> findByServiceTypeAndStatus(ServiceType serviceType, JobPostingStatus status, Pageable pageable);
    List<JobPosting> findByEndDateBeforeAndStatusNot(LocalDate endDate, JobPostingStatus status);
    
    @Query("SELECT jp FROM JobPosting jp WHERE " +
           "LOWER(jp.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(jp.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<JobPosting> search(@Param("query") String query, Pageable pageable);
}
