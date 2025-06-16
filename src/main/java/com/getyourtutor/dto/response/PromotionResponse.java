package com.getyourtutor.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PromotionResponse {
    private Long promotionId;
    private String code;
    private Double discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdByUsername;
    private LocalDateTime createdAt;
}
