package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Privilege;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends BaseRepository<Privilege, Long> {
    Optional<Privilege> findByName(String name);
}
