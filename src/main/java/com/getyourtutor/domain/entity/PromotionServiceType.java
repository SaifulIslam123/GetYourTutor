package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "promotion_service_types")
@Getter
@Setter
public class PromotionServiceType extends BaseEntity {
    @EmbeddedId
    private PromotionServiceTypeId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("serviceTypeId")
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;
}

@Embeddable
class PromotionServiceTypeId implements java.io.Serializable {
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
