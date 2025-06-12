package com.getyourtutor.domain.entity;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class PromotionUserId implements java.io.Serializable {
    private Long promotionId;
    private UUID userId;
    
    // Constructors, equals, and hashCode methods
    public PromotionUserId() {}
    
    public PromotionUserId(Long promotionId, UUID userId) {
        this.promotionId = promotionId;
        this.userId = userId;
    }
    
    // Getters and setters
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionUserId that = (PromotionUserId) o;
        return promotionId.equals(that.promotionId) &&
               userId.equals(that.userId);
    }
    
    @Override
    public int hashCode() {
        return 31 * promotionId.hashCode() + userId.hashCode();
    }
}
