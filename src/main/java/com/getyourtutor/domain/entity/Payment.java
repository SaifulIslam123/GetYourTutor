package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;


@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payer_user_id", nullable = false)
    private User payer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payee_user_id", nullable = false)
    private User payee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;



    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;


    @Column(unique = true)
    private String transactionId;

    @Column(precision = 10, scale = 2)
    private BigDecimal commissionAmount;
}

public enum PaymentStatus {
    PENDING, COMPLETED, FAILED, REFUNDED
}

enum PaymentMethod {
    CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, PAYPAL, CASH, OTHER
}
