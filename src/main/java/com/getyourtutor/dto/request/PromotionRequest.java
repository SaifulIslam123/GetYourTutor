package com.getyourtutor.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PromotionRequest {

    @NotBlank
    private String code;

    @NotNull
    @Min(1)
    @Max(100)
    private Integer discountPercentage;

    @NotNull
    @FutureOrPresent
    private Date startDate;

    @NotNull
    private Date endDate;
}
