package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "promotion_users")
@Getter
@Setter
public class PromotionUser extends BaseEntity {
    @EmbeddedId
    private PromotionUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("promotionId")
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private LocalDateTime dateUsed;

    @Column
    private Boolean isUsed = false;
}

