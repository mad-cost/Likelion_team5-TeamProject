package com.example.homeGym.instructor.controller;

import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.CommentDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor/review")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final AuthenticationFacade facade;

    // 강사 답글 페이지
    @GetMapping("{reviewId}/comment")
    public String createReviewPage(
            @PathVariable("reviewId") Long reviewId,
            Model model
    ) {
        Instructor instructor = facade.getCurrentInstructor();
        model.addAttribute("instructorId", instructor.getId());
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("commentDto", new CommentDto()); // 빈 CommentDto 객체 추가
        return "/instructor/review/create-review";
    }

    // 강사 답글 처리
    @PostMapping("{reviewId}/comment")
    public String createReview(
            @PathVariable("reviewId") Long reviewId,
            @ModelAttribute CommentDto commentDto,
            Model model
    ) {
        Instructor instructor = facade.getCurrentInstructor();
        commentService.createReview(instructor.getId(), reviewId, commentDto);
        return  "redirect:/instructor/reviews";
    }

    // 답글 수정 페이지
    @GetMapping("{reviewId}/comment/{commentId}")
    public String updateReviewPage(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            Model model
    ) {
        Instructor instructor = facade.getCurrentInstructor();
        model.addAttribute("instructorId", instructor.getId());
        model.addAttribute("reviewId", reviewId);
        model.addAttribute("commentId", commentId);
        CommentDto commentDto = commentService.getCommentDtoById(commentId); // 수정할 댓글 정보 가져오기
        model.addAttribute("commentDto", commentDto); // 가져온 댓글 정보 추가
        return "/instructor/review/update-review";
    }

    // 답글 수정 처리
    @PutMapping("{reviewId}/comment/{commentId}")
    public String updateReview(
            @PathVariable("reviewId") Long reviewId,
            @PathVariable("commentId") Long commentId,
            @ModelAttribute CommentDto commentDto
    ) {
        Instructor instructor = facade.getCurrentInstructor();
        commentService.updateReview(instructor.getId(), reviewId, commentDto);
        return "redirect:/instructor/reviews";
    }

    // 답글 삭제 처리
    @DeleteMapping("{reviewId}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long reviewId, @PathVariable Long commentId) {
        try {
            commentService.deleteReview(reviewId, commentId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting comment");
        }
    }

}
