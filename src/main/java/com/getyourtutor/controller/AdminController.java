package com.getyourtutor.controller;

import com.getyourtutor.dto.request.PromotionRequest;
import com.getyourtutor.dto.request.ServiceTypeRequest;
import com.getyourtutor.dto.response.PromotionResponse;
import com.getyourtutor.dto.response.ServiceTypeResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.PromotionService;
import com.getyourtutor.service.ServiceTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final ServiceTypeService serviceTypeService;
    private final PromotionService promotionService;

    // Service Type Management
    @PostMapping("/servicetypes")
    public ResponseEntity<ServiceTypeResponse> createServiceType(@Valid @RequestBody ServiceTypeRequest request) {
        ServiceTypeResponse response = serviceTypeService.createServiceType(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/servicetypes/{id}")
    public ResponseEntity<ServiceTypeResponse> getServiceTypeById(@PathVariable Long id) {
        ServiceTypeResponse response = serviceTypeService.getServiceTypeById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/servicetypes")
    public ResponseEntity<Page<ServiceTypeResponse>> getAllServiceTypes(Pageable pageable) {
        Page<ServiceTypeResponse> response = serviceTypeService.getAllServiceTypes(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/servicetypes/{id}")
    public ResponseEntity<ServiceTypeResponse> updateServiceType(@PathVariable Long id, @Valid @RequestBody ServiceTypeRequest request) {
        ServiceTypeResponse response = serviceTypeService.updateServiceType(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/servicetypes/{id}")
    public ResponseEntity<?> deleteServiceType(@PathVariable Long id) {
        serviceTypeService.deleteServiceType(id);
        return ResponseEntity.noContent().build();
    }

    // Promotion Management
    @PostMapping("/promotions")
    public ResponseEntity<PromotionResponse> createPromotion(@Valid @RequestBody PromotionRequest request, @AuthenticationPrincipal UserPrincipal currentUser) {
        PromotionResponse response = promotionService.createPromotion(request, currentUser.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/promotions/{id}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable Long id) {
        PromotionResponse response = promotionService.getPromotionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/promotions")
    public ResponseEntity<Page<PromotionResponse>> getAllPromotions(Pageable pageable) {
        Page<PromotionResponse> response = promotionService.getAllPromotions(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/promotions/{id}")
    public ResponseEntity<PromotionResponse> updatePromotion(@PathVariable Long id, @Valid @RequestBody PromotionRequest request) {
        PromotionResponse response = promotionService.updatePromotion(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/promotions/{id}")
    public ResponseEntity<?> deletePromotion(@PathVariable Long id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }
}
