package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Payment;
import com.getyourtutor.domain.entity.PaymentMethod;

import java.math.BigDecimal;

public interface PaymentService {
    Payment createPayment(Long jobId, BigDecimal amount, PaymentMethod method) throws Exception;

    Payment processPayment(Long paymentId, String transactionId) throws Exception;

    Payment failPayment(Long paymentId) throws Exception;
}
