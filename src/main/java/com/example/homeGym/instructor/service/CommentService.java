package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.CommentDto;
import com.example.homeGym.instructor.entity.Comment;

public interface CommentService {
    CommentDto createReview(Long instructorId, Long reviewId, CommentDto commentDto);

    CommentDto updateReview(Long instructorId, Long reviewId, CommentDto commentDto);

    void deleteReview(Long instructorId, Long reviewId);

    CommentDto getCommentDtoById(Long commentId);
}
