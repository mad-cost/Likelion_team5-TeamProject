package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.InstructorReviewDto;

public interface InstructorReviewService {
    InstructorReviewDto createReview(Long instructorId, InstructorReviewDto instructorReviewDto);

    InstructorReviewDto updateReview(Long instructorId, Long instructorReviewId, InstructorReviewDto reviewDto);

    void deleteReview(Long instructorId, Long instructorReviewId);
}
