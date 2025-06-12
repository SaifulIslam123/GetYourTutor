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

