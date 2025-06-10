package com.getyourtutor.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

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

@Embeddable
class PromotionUserId implements java.io.Serializable {
    private Long promotionId;
    private Long userId;
    
    // Constructors, equals, and hashCode methods
    public PromotionUserId() {}
    
    public PromotionUserId(Long promotionId, Long userId) {
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
