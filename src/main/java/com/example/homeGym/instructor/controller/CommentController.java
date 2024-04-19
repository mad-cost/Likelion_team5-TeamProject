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


    // 강사 답글
    @PostMapping("{reviewId}/comment")
    public CommentDto createReview(
            @PathVariable("instructorId")
            Long instructorId,
            @RequestBody
            CommentDto commentDto
    ) {
        return commentServiceImp.createReview(instructorId, commentDto);
    }

    // 답글 수정
    @PutMapping("{reviewId}/comment/{commentId}")
    public CommentDto updateReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("reviewId")
            Long reviewId,
            @PathVariable("commentId")
            Long commentId,
            @RequestBody
            CommentDto commentDto
    ) {

        return commentServiceImp.updateReview(instructorId, reviewId, commentDto);
    }

    // 답글 삭제
    @DeleteMapping("{reviewId}/comment/{commentId}")
    public void deleteReview(
            @PathVariable("instructorId")
            Long instructorId,
            @PathVariable("reviewId")
            Long reviewId,
            @PathVariable("commentId")
            Long commentId

    ) {
        commentServiceImp.deleteReview(instructorId, reviewId);
    }
}
