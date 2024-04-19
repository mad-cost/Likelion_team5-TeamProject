package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.CommentDto;
import com.example.homeGym.instructor.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor/{instructorId}/review")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 강사 답글 페이지
    @GetMapping("{reviewId}/comment")
    public String createReviewPage(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            Model model
    ) {
        model.addAttribute("instructorId", instructorId);
        model.addAttribute("reviewId", reviewId);
        return "create-review";
    }

    // 강사 답글 처리
    @PostMapping("{reviewId}/comment")
    public String createReview(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @ModelAttribute CommentDto commentDto
    ) {
        commentService.createReview(instructorId, commentDto);
        return "redirect:/instructor/{instructorId}/review/{reviewId}/comment";
    }

    // 답글 수정 페이지
    @GetMapping("{reviewId}/comment/{commentId}")
    public String updateReviewPage(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            Model model
    ) {
        model.addAttribute("instructorId", instructorId);
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("commentId", commentId);
        return "update-review";
    }

    // 답글 수정 처리
    @PutMapping("{reviewId}/comment/{commentId}")
    public String updateReview(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            @ModelAttribute CommentDto commentDto
    ) {
        commentService.updateReview(instructorId, reviewId, commentDto);
        return "redirect:/instructor/{instructorId}/review/{reviewId}/comment";
    }

    // 답글 삭제 처리
    @DeleteMapping("{reviewId}/comment/{commentId}")
    public String deleteReview(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId
    ) {
        commentService.deleteReview(instructorId, commentId);
        return "redirect:/instructor/{instructorId}/review/{reviewId}/comment";
    }
}
