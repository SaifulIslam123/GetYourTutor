package com.getyourtutor.dto.response;

import com.getyourtutor.domain.entity.ApplicationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobApplicationResponse {
    private Long applicationId;
    private ApplicationStatus status;
    private UserResponse tutor;
    private String coverLetter;
}
