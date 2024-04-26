package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewIdIn(List<Long> reviewIds);
    Comment findByReviewId(Long id);
}
