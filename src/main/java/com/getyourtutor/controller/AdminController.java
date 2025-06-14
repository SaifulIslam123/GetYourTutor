package com.getyourtutor.controller;

import com.getyourtutor.dto.request.ApprovalActionRequest;
import com.getyourtutor.dto.request.PromotionRequest;
import com.getyourtutor.dto.request.ServiceTypeRequest;
import com.getyourtutor.dto.response.ApprovalResponse;
import com.getyourtutor.dto.response.PromotionResponse;
import com.getyourtutor.dto.response.ServiceTypeResponse;
import com.getyourtutor.security.UserPrincipal;
import com.getyourtutor.service.ApprovalService;
import com.getyourtutor.service.PromotionService;
import com.getyourtutor.service.ServiceTypeService;
import com.getyourtutor.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ApprovalService approvalService;
    private final PromotionService promotionService;
    private final ServiceTypeService serviceTypeService;

    // Approval Endpoints
    @GetMapping("/approvals/pending")
    public ResponseEntity<Page<ApprovalResponse>> getPendingApprovals(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<ApprovalResponse> approvals = approvalService.getPendingApprovals(pageable);
        return ResponseEntity.ok(approvals);
    }

    @PostMapping("/approvals/{approvalId}/approve")
    public ResponseEntity<ApprovalResponse> approveRequest(
            @PathVariable Long approvalId,
            @RequestBody ApprovalActionRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        ApprovalResponse approvalResponse = approvalService.approve(approvalId, request, currentUser.getUsername());
        return ResponseEntity.ok(approvalResponse);
    }

    @PostMapping("/approvals/{approvalId}/reject")
    public ResponseEntity<ApprovalResponse> rejectRequest(
            @PathVariable Long approvalId,
            @RequestBody ApprovalActionRequest request,
            @AuthenticationPrincipal UserPrincipal currentUser) {
        ApprovalResponse approvalResponse = approvalService.reject(approvalId, request, currentUser.getUsername());
        return ResponseEntity.ok(approvalResponse);
    }

    // Promotion Endpoints
    @PostMapping("/promotions")
    public ResponseEntity<PromotionResponse> createPromotion(@Valid @RequestBody PromotionRequest promotionRequest,
                                                             @AuthenticationPrincipal UserPrincipal currentUser) {
        PromotionResponse promotionResponse = promotionService.createPromotion(promotionRequest, currentUser.getUsername());
        return ResponseEntity.ok(promotionResponse);
    }

    @GetMapping("/promotions")
    public ResponseEntity<Page<PromotionResponse>> getAllPromotions(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<PromotionResponse> promotions = promotionService.getAllPromotions(pageable);
        return ResponseEntity.ok(promotions);
    }

    @GetMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionResponse> getPromotionById(@PathVariable Long promotionId) {
        PromotionResponse promotionResponse = promotionService.getPromotionById(promotionId);
        return ResponseEntity.ok(promotionResponse);
    }

    @PutMapping("/promotions/{promotionId}")
    public ResponseEntity<PromotionResponse> updatePromotion(@PathVariable Long promotionId,
                                                             @Valid @RequestBody PromotionRequest promotionRequest) {
        PromotionResponse promotionResponse = promotionService.updatePromotion(promotionId, promotionRequest);
        return ResponseEntity.ok(promotionResponse);
    }

    @DeleteMapping("/promotions/{promotionId}")
    public ResponseEntity<Void> deletePromotion(@PathVariable Long promotionId) {
        promotionService.deletePromotion(promotionId);
        return ResponseEntity.noContent().build();
    }

    // Service Type Endpoints
    @PostMapping("/servicetypes")
    public ResponseEntity<ServiceTypeResponse> createServiceType(@Valid @RequestBody ServiceTypeRequest serviceTypeRequest) {
        ServiceTypeResponse serviceTypeResponse = serviceTypeService.createServiceType(serviceTypeRequest);
        return ResponseEntity.ok(serviceTypeResponse);
    }

    @GetMapping("/servicetypes")
    public ResponseEntity<Page<ServiceTypeResponse>> getAllServiceTypes(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "name");
        Page<ServiceTypeResponse> serviceTypes = serviceTypeService.getAllServiceTypes(pageable);
        return ResponseEntity.ok(serviceTypes);
    }

    @GetMapping("/servicetypes/{serviceTypeId}")
    public ResponseEntity<ServiceTypeResponse> getServiceTypeById(@PathVariable Long serviceTypeId) {
        ServiceTypeResponse serviceTypeResponse = serviceTypeService.getServiceTypeById(serviceTypeId);
        return ResponseEntity.ok(serviceTypeResponse);
    }

    @PutMapping("/servicetypes/{serviceTypeId}")
    public ResponseEntity<ServiceTypeResponse> updateServiceType(@PathVariable Long serviceTypeId,
                                                                 @Valid @RequestBody ServiceTypeRequest serviceTypeRequest) {
        ServiceTypeResponse serviceTypeResponse = serviceTypeService.updateServiceType(serviceTypeId, serviceTypeRequest);
        return ResponseEntity.ok(serviceTypeResponse);
    }

    @DeleteMapping("/servicetypes/{serviceTypeId}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long serviceTypeId) {
        serviceTypeService.deleteServiceType(serviceTypeId);
        return ResponseEntity.noContent().build();
    }
}
