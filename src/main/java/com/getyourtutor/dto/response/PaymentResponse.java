package com.getyourtutor.dto.response;

import com.getyourtutor.domain.PaymentStatus;
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
public class PaymentResponse {
    private Long paymentId;
    private Long jobId;
    private BigDecimal amount;
    private PaymentStatus status;
    private Date paidAt;
}
