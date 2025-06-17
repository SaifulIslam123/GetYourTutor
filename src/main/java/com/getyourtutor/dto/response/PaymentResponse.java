package com.getyourtutor.dto.response;

import com.getyourtutor.domain.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long jobId;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paidAt;
}
