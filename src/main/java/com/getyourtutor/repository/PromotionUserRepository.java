package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.PromotionUser;
import com.getyourtutor.domain.entity.PromotionUserId;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionUserRepository extends BaseRepository<PromotionUser, PromotionUserId> {
}
