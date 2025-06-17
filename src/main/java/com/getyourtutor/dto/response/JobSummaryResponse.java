package com.getyourtutor.dto.response;

import com.getyourtutor.domain.entity.JobStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobSummaryResponse {
    private Long jobId;
    private String title;
    private JobStatus status;
}
