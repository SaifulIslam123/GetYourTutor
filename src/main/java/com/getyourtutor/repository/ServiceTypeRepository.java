package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.ServiceType;
import com.getyourtutor.domain.entity.ServiceTypeName;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceTypeRepository extends BaseRepository<ServiceType, Long> {
    Optional<ServiceType> findByName(ServiceTypeName name);
    List<ServiceType> findByIsActive(boolean isActive);
    boolean existsByName(ServiceTypeName name);
}
