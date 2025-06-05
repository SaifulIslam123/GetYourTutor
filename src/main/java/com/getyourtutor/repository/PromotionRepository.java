package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Promotion;
import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PromotionRepository extends BaseRepository<Promotion, Long> {
    Optional<Promotion> findByCode(String code);
    Page<Promotion> findByIsActive(boolean isActive, Pageable pageable);
    Page<Promotion> findByCreatedBy(User createdBy, Pageable pageable);
    
    @Query("SELECT p FROM Promotion p WHERE " +
           "p.isActive = true AND " +
           "p.startDate <= CURRENT_DATE AND " +
           "(p.endDate IS NULL OR p.endDate >= CURRENT_DATE) AND " +
           "(p.maxUses IS NULL OR p.currentUses < p.maxUses) AND " +
           "(:serviceType IS NULL OR EXISTS (SELECT 1 FROM p.promotionServiceTypes pst WHERE pst.serviceType = :serviceType))")
    Page<Promotion> findActivePromotions(
            @Param("serviceType") ServiceType serviceType,
            Pageable pageable);
            
    @Query("SELECT COUNT(p) > 0 FROM Promotion p JOIN p.promotionUsers pu WHERE p = :promotion AND pu.user = :user")
    boolean isPromotionUsedByUser(@Param("promotion") Promotion promotion, @Param("user") User user);
}
