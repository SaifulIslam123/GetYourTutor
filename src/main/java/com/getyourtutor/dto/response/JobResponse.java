package com.getyourtutor.dto.response;

import com.getyourtutor.domain.JobStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JobResponse {
    private Long jobId;
    private String title;
    private String description;
    private JobStatus status;
    private UserResponse consumer;
    private UserResponse tutor;
}
