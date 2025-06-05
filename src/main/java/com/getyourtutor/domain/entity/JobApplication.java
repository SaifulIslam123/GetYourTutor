package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "job_applications")
@Getter
@Setter
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobApplicationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tutor_user_id", nullable = false)
    private User tutor;

    @Column(nullable = false)
    private LocalDateTime applicationDate = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String coverLetter;

    @Column
    private Double proposedRate;

    @OneToOne(mappedBy = "jobApplication", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Job job;
}

enum ApplicationStatus {
    PENDING, ACCEPTED, REJECTED
}
