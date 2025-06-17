package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Promotion;
import com.getyourtutor.dto.request.PromotionRequest;
import com.getyourtutor.dto.response.PromotionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PromotionService {
    Promotion createPromotion(Promotion promotion, String creatorUsername) throws Exception;

    PromotionResponse createPromotion(PromotionRequest request, String creatorUsername);

    PromotionResponse getPromotionById(Long id);

    Page<PromotionResponse> getAllPromotions(Pageable pageable);

    PromotionResponse updatePromotion(Long id, PromotionRequest request);

    void deletePromotion(Long id);
}
