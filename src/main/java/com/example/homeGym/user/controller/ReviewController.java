package com.example.homeGym.user.controller;

import com.example.homeGym.common.util.AuthenticationUtilService;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class ReviewController {

    private final ReviewService reviewService;
    private final AuthenticationUtilService authenticationUtilService;

    @GetMapping("review/{userProgramId}")
    public String reviewPage(
            @PathVariable("userProgramId")
            Long id,
            Model model
    ){
        model.addAttribute("userProgramId", id);
        return "/user/review";
    }

    @PostMapping("review")
    public String reviewWrite(
            @RequestParam(value = "images", required = false)
            List<MultipartFile> images,
            @RequestParam("memo")
            String memo,
            @RequestParam("rating")
            Integer rating,
            @RequestParam("userProgramId")
            Long userProgramId,
            Authentication authentication
    ){
        Long userId = authenticationUtilService.getId(authentication);

        try {
            ReviewDto reviewDto = reviewService.createReview(userId, userProgramId, images, rating, memo);
            return "redirect:/user/program/" + userProgramId ;
        }catch (IOException e){
            return "redirect:/user/program/" + userProgramId ;
        }
    }

    @DeleteMapping("review")
    public String deleteReview(
            @RequestParam("reviewId")
            Long reviewId
    ){

        Long userProgramId = reviewService.getUserProgramId(reviewId);
        reviewService.deleteReview(reviewId);

        return "redirect:/user/program/" + userProgramId ;

    }

    @PutMapping("review")
    public String updateReviewPage(
            @RequestParam("reviewId")
            Long reviewId,
            Model model
    ){

        model.addAttribute("review", reviewService.updateReview(reviewId));
        return "user/updatereview";
    }

    @PostMapping("review/update")
    public String updateReview(
            @RequestParam(value = "images", required = false)
            List<MultipartFile> images,
            @RequestParam("memo")
            String memo,
            @RequestParam("rating")
            Integer rating,
            @RequestParam("reviewId")
            Long reviewId,
            Authentication authentication

    ){
        Long userId = authenticationUtilService.getId(authentication);

        reviewService.updateReview(userId, reviewId, images, rating, memo);
        Long userProgramId = reviewService.getUserProgramId(reviewId);

        return "redirect:/user/program/" + userProgramId ;
    }
}
