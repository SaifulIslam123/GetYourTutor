package com.getyourtutor.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class JobPostingRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String subject;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal budget;

    @NotNull
    @Future
    private Date deadline;
}
