package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "service_types")
@Getter
@Setter
public class ServiceType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceTypeId;

    @Column(length = 50, unique = true, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Boolean isActive = true;

    @OneToMany(mappedBy = "serviceType", fetch = FetchType.LAZY)
    private Set<JobPosting> jobPostings = new HashSet<>();

    @OneToMany(mappedBy = "serviceType", fetch = FetchType.LAZY)
    private Set<PromotionServiceType> promotionServiceTypes = new HashSet<>();
}

