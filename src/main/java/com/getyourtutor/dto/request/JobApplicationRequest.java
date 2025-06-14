package com.getyourtutor.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class JobApplicationRequest {

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal proposedRate;

    @NotBlank
    private String coverLetter;
}
