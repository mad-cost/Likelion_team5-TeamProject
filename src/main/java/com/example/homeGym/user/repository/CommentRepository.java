package com.example.homeGym.user.repository;

import com.example.homeGym.instructor.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
