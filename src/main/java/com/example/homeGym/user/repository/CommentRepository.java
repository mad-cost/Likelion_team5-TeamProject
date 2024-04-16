package com.example.homeGym.user.repository;

import com.example.homeGym.user.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
