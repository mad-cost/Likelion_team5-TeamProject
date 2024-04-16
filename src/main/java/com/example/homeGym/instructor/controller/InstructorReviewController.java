package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.InstructorReviewDto;
import com.example.homeGym.instructor.service.InstructorReviewServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor/{instructorId}/review")
@RequiredArgsConstructor
public class InstructorReviewController {
    private final InstructorReviewServiceImp instructorReviewServiceImp;

    // 강사 후기 페이지

    @PostMapping
    public void ViewReview() {

    }

    // 강사 답글
    @PostMapping("{reviewId}")
    public InstructorReviewDto createReview(
            @PathVariable("instructorId")
            Long instructorId,
            @RequestBody
            InstructorReviewDto instructorReviewDto
    ) {
        return instructorReviewServiceImp.createReview(instructorId, instructorReviewDto);
    }

    // 답글 수정
    @PutMapping("{reviewId}")
    public InstructorReviewDto updateReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("instructorReviewId")
            Long instructorReviewId,
            @RequestBody
            InstructorReviewDto instructorReviewDto
    ) {

        return instructorReviewServiceImp.updateReview(instructorId, instructorReviewId, instructorReviewDto);
    }

    // 답글 삭제
    @DeleteMapping("{reviewId}")
    public void deleteReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("instructorReviewId")
            Long instructorReviewId

    ) {
        instructorReviewServiceImp.deleteReview(instructorId, instructorReviewId);
    }
}
