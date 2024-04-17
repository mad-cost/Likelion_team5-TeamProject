package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserProgramIdAndUserId(Long userProgramId, Long userId);
}
