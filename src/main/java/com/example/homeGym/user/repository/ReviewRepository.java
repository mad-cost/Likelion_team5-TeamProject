package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
