package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.PromotionServiceType;
import com.getyourtutor.domain.entity.PromotionServiceTypeId;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionServiceTypeRepository extends BaseRepository<PromotionServiceType, PromotionServiceTypeId> {
}
