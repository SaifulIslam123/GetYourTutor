package com.getyourtutor.service.impl;

import com.getyourtutor.domain.entity.Promotion;
import com.getyourtutor.domain.entity.User;
import com.getyourtutor.repository.PromotionRepository;
import com.getyourtutor.repository.UserRepository;
import com.getyourtutor.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
