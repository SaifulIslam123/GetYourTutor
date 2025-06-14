package com.getyourtutor.dto.response;

import com.getyourtutor.domain.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class JobApplicationResponse {
    private Long jobApplicationId;
    private Long jobId;
    private String tutorUsername;
    private BigDecimal proposedRate;
    private String coverLetter;
    private ApplicationStatus status;
    private Date appliedAt;
}
