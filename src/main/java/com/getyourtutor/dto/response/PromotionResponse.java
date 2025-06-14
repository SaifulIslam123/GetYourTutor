package com.getyourtutor.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PromotionResponse {
    private Long promotionId;
    private String code;
    private Integer discountPercentage;
    private Date startDate;
    private Date endDate;
    private String createdByUsername;
    private Date createdAt;
}
