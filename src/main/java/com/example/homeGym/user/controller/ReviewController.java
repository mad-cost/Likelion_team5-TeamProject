package com.example.homeGym.user.controller;

import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.entity.Review;
import com.example.homeGym.user.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("review")
    public String reviewPage(
            @RequestParam("userProgramId")
            Long id,
            Model model
    ){
        model.addAttribute("userProgramId", id);
        return "/user/review";
    }

    @PostMapping("review")
    @ResponseBody
    public String reviewWrite(
            @RequestParam(value = "images", required = false)
            List<MultipartFile> images,
            @RequestParam("memo")
            String memo,
            @RequestParam("rating")
            Integer rating,
            @RequestParam("userProgramId")
            Long userProgramId
    ){
        System.out.println("images = " + images);
        System.out.println("memo = " + memo);
        System.out.println("rating = " + rating);
        System.out.println("userProgramId = " + userProgramId);
        try {
            ReviewDto reviewDto = reviewService.createReview(1L, userProgramId, images, rating, memo);
            return "good";
        }catch (IOException e){
            return "error";
        }
    }

    @DeleteMapping("review")
    @ResponseBody
    public String deleteReview(
            @RequestParam("reviewId")
            Long reviewId
    ){
//        reviewService.updateReview();
        reviewService.deleteReview(reviewId);
        return "delete";
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
    @ResponseBody
    public String updateReview(
            @RequestParam(value = "images", required = false)
            List<MultipartFile> images,
            @RequestParam("memo")
            String memo,
            @RequestParam("rating")
            Integer rating,
            @RequestParam("reviewId")
            Long reviewId

    ){
//        System.out.println("images = " + images);
//        if (images != null){
//            for (MultipartFile image :
//                    images) {
//                System.out.println(image.getOriginalFilename());
//                System.out.println(image.getSize());
//            }
//        }
        reviewService.updateReview(1L, reviewId, images, rating, memo);

        return "re";
    }
}
