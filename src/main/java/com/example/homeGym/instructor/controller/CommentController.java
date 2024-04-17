package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.CommentDto;
import com.example.homeGym.instructor.service.CommentServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor/{instructorId}/review")
@RequiredArgsConstructor
public class CommentController {
    private final CommentServiceImp commentServiceImp;

    // 강사 후기 페이지

    @PostMapping
    public void ViewReview() {

    }

    // 강사 답글
    @PostMapping("{reviewId}")
    public CommentDto createReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("reviewId")
            Long reviewId,
            @RequestBody
            CommentDto commentDto
    ) {
        return commentServiceImp.createReview(instructorId, commentDto);
    }

    // 답글 수정
    @PutMapping("{reviewId}")
    public CommentDto updateReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("reviewId")
            Long reviewId,
            @RequestBody
            CommentDto commentDto
    ) {

        return commentServiceImp.updateReview(instructorId, reviewId, commentDto);
    }

    // 답글 삭제
    @DeleteMapping("{reviewId}")
    public void deleteReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("reviewId")
            Long reviewId

    ) {
        commentServiceImp.deleteReview(instructorId, reviewId);
    }
}
