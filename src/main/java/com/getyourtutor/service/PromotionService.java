package com.getyourtutor.service;

import com.getyourtutor.domain.entity.Promotion;

public interface PromotionService {
    Promotion createPromotion(Promotion promotion, String creatorUsername) throws Exception;
}
