package com.getyourtutor.service;

import com.getyourtutor.dto.request.PromotionRequest;
import com.getyourtutor.dto.response.PromotionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
    PromotionResponse createPromotion(PromotionRequest request, String creatorUsername);
    PromotionResponse updatePromotion(Long promotionId, PromotionRequest request);
    void deletePromotion(Long promotionId);
    PromotionResponse getPromotionById(Long promotionId);
    Page<PromotionResponse> getAllPromotions(Pageable pageable);
}
