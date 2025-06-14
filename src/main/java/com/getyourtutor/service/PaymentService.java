package com.getyourtutor.service;

import com.getyourtutor.dto.request.PaymentRequest;
import com.getyourtutor.dto.response.PaymentResponse;

public interface PaymentService {
    PaymentResponse initiatePayment(PaymentRequest paymentRequest, String username);
    PaymentResponse getPaymentDetails(Long paymentId, String username);
}
