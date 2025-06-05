package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Payment;
import com.getyourtutor.domain.entity.PaymentStatus;
import com.getyourtutor.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends BaseRepository<Payment, Long> {
    Page<Payment> findByPayer(User payer, Pageable pageable);
    Page<Payment> findByPayee(User payee, Pageable pageable);
    Page<Payment> findByStatus(PaymentStatus status, Pageable pageable);
    
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM Payment p WHERE p.payee = :user AND p.status = 'COMPLETED'")
    BigDecimal getTotalEarningsByUser(@Param("user") User user);
    
    @Query("SELECT p FROM Payment p WHERE " +
           "(p.payer = :user OR p.payee = :user) AND " +
           "(:startDate IS NULL OR p.paymentDate >= :startDate) AND " +
           "(:endDate IS NULL OR p.paymentDate <= :endDate) AND " +
           "(:status IS NULL OR p.status = :status)")
    Page<Payment> findPaymentsByUserAndFilters(
            @Param("user") User user,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") PaymentStatus status,
            Pageable pageable);
}
