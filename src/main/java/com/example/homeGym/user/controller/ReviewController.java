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
            @RequestParam("image")
            MultipartFile image,
            @RequestParam("userProgramId")
            Long userProgramId
    ){
        try {
            ReviewDto reviewDto = reviewService.createReview(1L, userProgramId, image);
            return "good";
        }catch (IOException e){
            return "error";
        }
    }

    @DeleteMapping("review")
    @ResponseBody
    public String deleteReview(){
        return "delete";
    }

    @PutMapping("review")
    @ResponseBody
    public String updateReview(){
        return "update";
    }

}
