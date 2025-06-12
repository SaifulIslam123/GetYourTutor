package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Approval;

public interface ApprovalService {
    Approval createApproval(Approval approval);
    Approval approve(Long approvalId, String approverUsername, String comments) throws Exception;
    Approval reject(Long approvalId, String rejecterUsername, String comments) throws Exception;
}
