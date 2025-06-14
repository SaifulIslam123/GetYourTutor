package com.getyourtutor.service;

import com.getyourtutor.dto.request.ApprovalActionRequest;
import com.getyourtutor.dto.response.ApprovalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApprovalService {
    Page<ApprovalResponse> getPendingApprovals(Pageable pageable);
    ApprovalResponse approve(Long approvalId, ApprovalActionRequest request, String approverUsername);
    ApprovalResponse reject(Long approvalId, ApprovalActionRequest request, String rejecterUsername);
}
