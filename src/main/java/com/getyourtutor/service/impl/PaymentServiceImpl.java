package com.getyourtutor.service.impl;

import com.getyourtutor.domain.JobStatus;
import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.Payment;
import com.getyourtutor.domain.PaymentMethod;
import com.getyourtutor.domain.PaymentStatus;
import com.getyourtutor.dto.request.PaymentRequest;
import com.getyourtutor.dto.response.PaymentResponse;
import com.getyourtutor.exception.BadRequestException;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.JobRepository;
import com.getyourtutor.repository.PaymentRepository;
import com.getyourtutor.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final JobRepository jobRepository;
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.10"); // 10% commission

    @Override
    @Transactional
    public PaymentResponse initiatePayment(PaymentRequest paymentRequest, String username) {
        Job job = jobRepository.findById(paymentRequest.getJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + paymentRequest.getJobId()));

        if (!job.getConsumer().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to make a payment for this job.");
        }

        if (job.getStatus() != JobStatus.COMPLETED) {
            throw new BadRequestException("Payments can only be initiated for completed jobs.");
        }

        BigDecimal amount = BigDecimal.valueOf(job.getTotalHours()).multiply(BigDecimal.valueOf(job.getHourlyRate()));

        Payment payment = new Payment();
        payment.setJob(job);
        payment.setPayer(job.getConsumer());
        payment.setPayee(job.getTutor());
        payment.setAmount(amount);
        payment.setMethod(PaymentMethod.CARD); // Defaulting to CARD for now
        payment.setStatus(PaymentStatus.PENDING);

        BigDecimal commission = amount.multiply(COMMISSION_RATE);
        payment.setCommissionAmount(commission);

        Payment savedPayment = paymentRepository.save(payment);
        return mapToPaymentResponse(savedPayment);
    }

    @Override
    @Transactional(readOnly = true)
    public PaymentResponse getPaymentDetails(Long paymentId, String username) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));

        if (!payment.getPayer().getUsername().equals(username) && !payment.getPayee().getUsername().equals(username)) {
            throw new AccessDeniedException("You are not authorized to view this payment.");
        }

        return mapToPaymentResponse(payment);
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .jobId(payment.getJob().getJobId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paidAt(payment.getUpdatedAt()) // Assuming updatedAt is the payment date
                .build();
    }
}
