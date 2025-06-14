package com.getyourtutor.service.impl;

import com.getyourtutor.domain.ApprovalStatus;
import com.getyourtutor.domain.entity.Approval;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.ApprovalActionRequest;
import com.getyourtutor.dto.response.ApprovalResponse;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.ApprovalRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.ApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ApprovalServiceImpl implements ApprovalService {

    private final ApprovalRepository approvalRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<ApprovalResponse> getPendingApprovals(Pageable pageable) {
        return approvalRepository.findByStatus(ApprovalStatus.PENDING, pageable).map(this::mapToApprovalResponse);
    }

    @Override
    @Transactional
    public ApprovalResponse approve(Long approvalId, ApprovalActionRequest request, String approverUsername) {
        Approval approval = findApprovalById(approvalId);
        User approver = findUserByUsername(approverUsername);

        approval.setStatus(ApprovalStatus.APPROVED);
        approval.setApprovedBy(approver);
        approval.setApprovalDate(LocalDateTime.now());
        approval.setComments(request.getComments());

        Approval savedApproval = approvalRepository.save(approval);
        return mapToApprovalResponse(savedApproval);
    }

    @Override
    @Transactional
    public ApprovalResponse reject(Long approvalId, ApprovalActionRequest request, String rejecterUsername) {
        Approval approval = findApprovalById(approvalId);
        User rejecter = findUserByUsername(rejecterUsername);

        approval.setStatus(ApprovalStatus.REJECTED);
        approval.setApprovedBy(rejecter);
        approval.setApprovalDate(LocalDateTime.now());
        approval.setComments(request.getComments());

        Approval savedApproval = approvalRepository.save(approval);
        return mapToApprovalResponse(savedApproval);
    }

    private Approval findApprovalById(Long approvalId) {
        return approvalRepository.findById(approvalId)
                .orElseThrow(() -> new ResourceNotFoundException("Approval not found with ID: " + approvalId));
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
    }

    private ApprovalResponse mapToApprovalResponse(Approval approval) {
        return ApprovalResponse.builder()
                .approvalId(approval.getApprovalId())
                .entityType(approval.getEntityType())
                .entityId(approval.getEntityId())
                .status(approval.getStatus())
                .requestedByUsername(approval.getRequestedBy().getUsername())
                .approvedByUsername(approval.getApprovedBy() != null ? approval.getApprovedBy().getUsername() : null)
                .approvalDate(approval.getApprovalDate())
                .comments(approval.getComments())
                .createdAt(approval.getCreatedAt())
                .build();
    }
}
