package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.User;
import com.getyourtutor.domain.entity.UserType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends BaseRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByUserType(UserType userType);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
