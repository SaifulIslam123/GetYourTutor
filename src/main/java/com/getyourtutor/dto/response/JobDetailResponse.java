package com.getyourtutor.dto.response;

import com.getyourtutor.domain.JobStatus;
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
public class JobDetailResponse {
    private Long jobId;
    private String title;
    private String description;
    private String subject;
    private JobStatus status;
    private String consumerUsername;
    private String tutorUsername;
    private Date startDate;
    private Date endDate;
    private BigDecimal hourlyRate;
    private Date createdAt;
}
