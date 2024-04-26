package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserProgramIdAndUserId(Long userProgramId, Long userId);

    List<Review> findAllByUserProgramIdAndUserId(Long userProgramId, Long userId);
    @Query("SELECT r FROM Review r JOIN UserProgram up ON r.userProgramId = up.id JOIN Program p ON up.programId = p.id WHERE p.instructorId = :instructorId")
    Page<Review> findByInstructorId(@Param("instructorId") Long instructorId, Pageable pageable);

    Review findByUserProgramId(Long userProgramId);
}
