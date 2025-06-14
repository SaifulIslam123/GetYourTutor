package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {
    Optional<Role> findByName(String name);
    Optional<Role> findByName(String name);
}
