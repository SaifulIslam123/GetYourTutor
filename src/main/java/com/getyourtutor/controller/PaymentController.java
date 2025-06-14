package com.getyourtutor.controller;

import com.getyourtutor.dto.request.PaymentRequest;
import com.getyourtutor.dto.response.PaymentResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/initiate")
    @PreAuthorize("hasRole('CONSUMER')")
    public ResponseEntity<PaymentResponse> initiatePayment(@Valid @RequestBody PaymentRequest paymentRequest,
                                                             @AuthenticationPrincipal UserPrincipal currentUser) {
        PaymentResponse paymentResponse = paymentService.initiatePayment(paymentRequest, currentUser.getUsername());
        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/{paymentId}")
    @PreAuthorize("hasRole('CONSUMER') or hasRole('TUTOR')")
    public ResponseEntity<PaymentResponse> getPaymentDetails(@PathVariable Long paymentId,
                                                               @AuthenticationPrincipal UserPrincipal currentUser) {
        PaymentResponse paymentResponse = paymentService.getPaymentDetails(paymentId, currentUser.getUsername());
        return ResponseEntity.ok(paymentResponse);
    }
}
