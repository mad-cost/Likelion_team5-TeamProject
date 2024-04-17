package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.CommentDto;

public interface CommentService {
    CommentDto createReview(Long instructorId, CommentDto reviewId);

    CommentDto updateReview(Long instructorId, Long reviewId, CommentDto commentDto);

    void deleteReview(Long instructorId, Long reviewId);
}
