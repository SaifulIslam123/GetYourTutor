package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserType userType;



    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<JobPosting> jobPostings = new HashSet<>();

    @OneToMany(mappedBy = "tutor", fetch = FetchType.LAZY)
    private Set<JobApplication> jobApplications = new HashSet<>();

    @OneToMany(mappedBy = "tutor", fetch = FetchType.LAZY)
    private Set<Job> tutorJobs = new HashSet<>();

    @OneToMany(mappedBy = "consumer", fetch = FetchType.LAZY)
    private Set<Job> consumerJobs = new HashSet<>();

    @OneToMany(mappedBy = "reviewedUser", fetch = FetchType.LAZY)
    private Set<Review> receivedReviews = new HashSet<>();

    @OneToMany(mappedBy = "reviewer", fetch = FetchType.LAZY)
    private Set<Review> givenReviews = new HashSet<>();

    @OneToMany(mappedBy = "createdBy", fetch = FetchType.LAZY)
    private Set<Promotion> createdPromotions = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<PromotionUser> promotionUsers = new HashSet<>();

    @OneToMany(mappedBy = "approvedBy", fetch = FetchType.LAZY)
    private Set<Approval> approvals = new HashSet<>();


}

public enum UserType {
    TUTOR, PARENT, ORGANIZATION, PROMOTER, ADMIN
}
