package com.getyourtutor.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationRequest {
    @NotNull
    private Long jobId;

    @Size(max = 2000)
    private String coverLetter;
}
