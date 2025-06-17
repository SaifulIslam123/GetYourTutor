package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Promotion;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.PromotionRequest;
import com.getyourtutor.dto.response.PromotionResponse;
import com.getyourtutor.repository.PromotionRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Promotion createPromotion(Promotion promotion, String creatorUsername) throws Exception {
        User creator = userRepository.findByUsername(creatorUsername)
                .orElseThrow(() -> new Exception("User not found: " + creatorUsername));

        // A promotion code should be unique.
        if (promotionRepository.findByCode(promotion.getCode()).isPresent()) {
            throw new Exception("A promotion with code '" + promotion.getCode() + "' already exists.");
        }

        promotion.setCreatedBy(creator);
        return promotionRepository.save(promotion);
    }

    // --- DTO methods ---
    @Override
    public PromotionResponse createPromotion(PromotionRequest request, String creatorUsername) {
        // TODO implement
        return PromotionResponse.builder()
                .promotionId(0L)
                .code(request.getCode())
                .discountPercentage(request.getDiscountPercentage())
                .build();
    }

    @Override
    public PromotionResponse getPromotionById(Long id) {
        return PromotionResponse.builder().promotionId(id).code("PROMO").build();
    }

    @Override
    public Page<PromotionResponse> getAllPromotions(Pageable pageable) {
        return new PageImpl<>(List.of());
    }

    @Override
    public PromotionResponse updatePromotion(Long id, PromotionRequest request) {
        return PromotionResponse.builder().promotionId(id).code(request.getCode()).discountPercentage(request.getDiscountPercentage()).build();
    }

    @Override
    public void deletePromotion(Long id) {
        // TODO
    }
}
