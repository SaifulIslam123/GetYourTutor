package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Promotion;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.dto.request.PromotionRequest;
import com.getyourtutor.dto.response.PromotionResponse;
import com.getyourtutor.exception.BadRequestException;
import com.getyourtutor.exception.ResourceNotFoundException;
import com.getyourtutor.repository.PromotionRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public PromotionResponse createPromotion(PromotionRequest request, String creatorUsername) {
        User creator = userRepository.findByUsername(creatorUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + creatorUsername));

        if (promotionRepository.findByCode(request.getCode()).isPresent()) {
            throw new BadRequestException("A promotion with code '" + request.getCode() + "' already exists.");
        }

        Promotion promotion = new Promotion();
        promotion.setCode(request.getCode());
        promotion.setDiscountPercentage(request.getDiscountPercentage());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setCreatedBy(creator);

        Promotion savedPromotion = promotionRepository.save(promotion);
        return mapToPromotionResponse(savedPromotion);
    }

    @Override
    @Transactional
    public PromotionResponse updatePromotion(Long promotionId, PromotionRequest request) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found with id: " + promotionId));

        promotionRepository.findByCode(request.getCode()).ifPresent(p -> {
            if (!p.getPromotionId().equals(promotionId)) {
                throw new BadRequestException("A promotion with code '" + request.getCode() + "' already exists.");
            }
        });

        promotion.setCode(request.getCode());
        promotion.setDiscountPercentage(request.getDiscountPercentage());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());

        Promotion updatedPromotion = promotionRepository.save(promotion);
        return mapToPromotionResponse(updatedPromotion);
    }

    @Override
    @Transactional
    public void deletePromotion(Long promotionId) {
        if (!promotionRepository.existsById(promotionId)) {
            throw new ResourceNotFoundException("Promotion not found with id: " + promotionId);
        }
        promotionRepository.deleteById(promotionId);
    }

    @Override
    @Transactional(readOnly = true)
    public PromotionResponse getPromotionById(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found with id: " + promotionId));
        return mapToPromotionResponse(promotion);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PromotionResponse> getAllPromotions(Pageable pageable) {
        return promotionRepository.findAll(pageable).map(this::mapToPromotionResponse);
    }

    private PromotionResponse mapToPromotionResponse(Promotion promotion) {
        return PromotionResponse.builder()
                .promotionId(promotion.getPromotionId())
                .code(promotion.getCode())
                .discountPercentage(promotion.getDiscountPercentage())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .createdByUsername(promotion.getCreatedBy().getUsername())
                .createdAt(promotion.getCreatedAt())
                .build();
    }
}
