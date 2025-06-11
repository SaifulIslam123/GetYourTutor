package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "approvals")
@Getter
@Setter
public class Approval extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long approvalId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalEntityType approvedEntityType;

    @Column(nullable = false)
    private Long approvedEntityId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus status = ApprovalStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_user_id")
    private User approvedBy;

    @Column
    private LocalDateTime approvalDate;

    @Column(columnDefinition = "TEXT")
    private String comments;

    @Column(columnDefinition = "TEXT")
    private String requiredDocuments;

    @OneToOne(mappedBy = "approval", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Job job;
}

