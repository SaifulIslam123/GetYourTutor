package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Approval;
import com.getyourtutor.domain.entity.ApprovalStatus;
import com.getyourtutor.domain.entity.ApprovalEntityType;
import com.getyourtutor.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    Page<Approval> findByStatus(ApprovalStatus status, Pageable pageable);
    Page<Approval> findByApprovedBy(User approvedBy, Pageable pageable);
    Page<Approval> findByStatusAndApprovedEntityType(ApprovalStatus status, ApprovalEntityType entityType, Pageable pageable);
    Optional<Approval> findByApprovedEntityTypeAndApprovedEntityId(ApprovalEntityType entityType, Long entityId);
    boolean existsByApprovedEntityTypeAndApprovedEntityId(ApprovalEntityType entityType, Long entityId);
}
