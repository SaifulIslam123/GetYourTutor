package com.getyourtutor.domain.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public class PromotionServiceTypeId implements java.io.Serializable {
    private Long promotionId;
    private Long serviceTypeId;
    
    // Constructors, equals, and hashCode methods
    public PromotionServiceTypeId() {}
    
    public PromotionServiceTypeId(Long promotionId, Long serviceTypeId) {
        this.promotionId = promotionId;
        this.serviceTypeId = serviceTypeId;
    }
    
    // Getters and setters
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotionServiceTypeId that = (PromotionServiceTypeId) o;
        return promotionId.equals(that.promotionId) &&
               serviceTypeId.equals(that.serviceTypeId);
    }
    
    @Override
    public int hashCode() {
        return 31 * promotionId.hashCode() + serviceTypeId.hashCode();
    }
}
