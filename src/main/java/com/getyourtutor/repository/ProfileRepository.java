package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Profile;
import com.getyourtutor.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends BaseRepository<Profile, Long> {
    Optional<Profile> findByUser(User user);
    boolean existsByUserId(Long userId);
}
