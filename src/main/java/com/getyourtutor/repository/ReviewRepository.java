package com.getyourtutor.repository;

import com.getyourtutor.domain.entity.Job;
import com.getyourtutor.domain.entity.Review;
import com.getyourtutor.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends BaseRepository<Review, Long> {

    Page<Review> findByReviewedUser(User reviewedUser, Pageable pageable);

    Page<Review> findByReviewer(User reviewer, Pageable pageable);

    Optional<Review> findByJob(Job job);

    boolean existsByJobAndReviewer(Job job, User reviewer);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.reviewedUser = :user AND r.isVisible = true")
    Double calculateAverageRating(@Param("user") User user);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.reviewedUser = :user AND r.isVisible = true")
    Long countVisibleReviews(@Param("user") User user);

    @Query("SELECT r FROM Review r WHERE " +
            "r.reviewedUser = :user AND " +
            "r.isVisible = true AND " +
            "(:rating IS NULL OR r.rating = :rating)")
    Page<Review> findVisibleReviewsByReviewedUserAndRating(
            @Param("user") User user,
            @Param("rating") Integer rating,
            Pageable pageable
    );
}
