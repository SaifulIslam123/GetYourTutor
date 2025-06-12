package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Approval;
import com.getyourtutor.domain.entity.ApprovalStatus;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.repository.ApprovalRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Approval createApproval(Approval approval) {
        approval.setStatus(ApprovalStatus.PENDING);
        return approvalRepository.save(approval);
    }

    @Override
    public Approval approve(Long approvalId, String approverUsername, String comments) throws Exception {
        Approval approval = findApprovalById(approvalId);
        User approver = findUserByUsername(approverUsername);

        approval.setStatus(ApprovalStatus.APPROVED);
        approval.setApprovedBy(approver);
        approval.setApprovalDate(LocalDateTime.now());
        approval.setComments(comments);

        return approvalRepository.save(approval);
    }

    @Override
    public Approval reject(Long approvalId, String rejecterUsername, String comments) throws Exception {
        Approval approval = findApprovalById(approvalId);
        User rejecter = findUserByUsername(rejecterUsername);

        approval.setStatus(ApprovalStatus.REJECTED);
        approval.setApprovedBy(rejecter); // 'approvedBy' also stores the user who rejected it
        approval.setApprovalDate(LocalDateTime.now());
        approval.setComments(comments);

        return approvalRepository.save(approval);
    }

    private Approval findApprovalById(Long approvalId) throws Exception {
        return approvalRepository.findById(approvalId)
                .orElseThrow(() -> new Exception("Approval not found with ID: " + approvalId));
    }

    private User findUserByUsername(String username) throws Exception {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new Exception("User not found: " + username));
    }
}
