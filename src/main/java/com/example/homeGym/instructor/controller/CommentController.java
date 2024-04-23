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
        model.addAttribute("commentDto", new CommentDto()); // 빈 CommentDto 객체 추가
        return "create-review";
    }

    // 강사 답글 처리
    @PostMapping("{reviewId}/comment")
    public String createReview(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @ModelAttribute CommentDto commentDto,
            Model model
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
        CommentDto commentDto = commentService.getCommentDtoById(commentId); // 수정할 댓글 정보 가져오기
        model.addAttribute("commentDto", commentDto); // 가져온 댓글 정보 추가
        return "update-review";
    }

    // 답글 수정 처리
    @PutMapping("{reviewId}/comment/{commentId}")
    public String updateReview(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            @ModelAttribute CommentDto commentDto,
            Model model
    ) {
        commentService.updateReview(instructorId, reviewId, commentDto);
        return "redirect:/instructor/{instructorId}/review/{reviewId}/comment";
    }

    // 답글 삭제 처리
    @DeleteMapping("{reviewId}/comment/{commentId}")
    public String deleteReview(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            Model model
    ) {
        commentService.deleteReview(instructorId, commentId);
        return "redirect:/instructor/{instructorId}/review/{reviewId}/comment";
    }
}
