package com.getyourtutor.dto.response;

import com.getyourtutor.domain.ApprovalStatus;
import com.getyourtutor.domain.entity.ApprovalEntityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApprovalResponse {
    private Long approvalId;
    private ApprovalEntityType entityType;
    private Long entityId;
    private ApprovalStatus status;
    private String requestedByUsername;
    private String approvedByUsername;
    private LocalDateTime approvalDate;
    private String comments;
    private LocalDateTime createdAt;
}
