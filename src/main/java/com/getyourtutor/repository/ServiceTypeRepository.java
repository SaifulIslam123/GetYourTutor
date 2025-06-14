package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.ServiceType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends BaseRepository<ServiceType, Long> {
    Optional<ServiceType> findByName(String name);
    List<ServiceType> findByIsActive(boolean isActive);
    boolean existsByName(String name);
}
