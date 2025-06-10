package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Profile;
import com.getyourtutor.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends BaseRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
    boolean existsByUserId(UUID userId);
}
