package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.Payment;
import com.getyourtutor.domain.entity.PaymentMethod;
import com.getyourtutor.domain.entity.PaymentStatus;
import com.getyourtutor.dto.request.PaymentRequest;
import com.getyourtutor.dto.response.PaymentResponse;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.repository.PaymentRepository;
import com.getyourtutor.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10"); // 10% commission

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Payment createPayment(Long jobId, BigDecimal amount, PaymentMethod method) throws Exception {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new Exception("Job not found with id: " + jobId));

        Payment payment = new Payment();
        payment.setJob(job);
        payment.setPayer(job.getConsumer());
        payment.setPayee(job.getTutor());
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setStatus(PaymentStatus.PENDING);

        BigDecimal commission = amount.multiply(COMMISSION_RATE);
        payment.setCommissionAmount(commission);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment processPayment(Long paymentId, String transactionId) throws Exception {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new Exception("Payment not found with id: " + paymentId));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new Exception("Only pending payments can be processed. Current status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setTransactionId(transactionId);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment failPayment(Long paymentId) throws Exception {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new Exception("Payment not found with id: " + paymentId));

        if (payment.getStatus() != PaymentStatus.PENDING) {
            throw new Exception("Only pending payments can be failed. Current status: " + payment.getStatus());
        }

        payment.setStatus(PaymentStatus.FAILED);

        return paymentRepository.save(payment);
    }

    @Override
    public PaymentResponse initiatePayment(PaymentRequest request, String consumerUsername) {
        // TODO implement
        return PaymentResponse.builder()
                .paymentId(0L)
                .jobId(request.getJobId())
                .amount(new java.math.BigDecimal("0"))
                .status(PaymentStatus.PENDING)
                .build();
    }

    @Override
    public PaymentResponse getPaymentDetails(Long paymentId, String username) {
        // TODO implement
        return PaymentResponse.builder().paymentId(paymentId).build();
    }
}
