package com.example.homeGym.instructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor/{instructorId}/review")
@RequiredArgsConstructor
public class ReviewController {

    // 강사 후기 페이지
    @PostMapping()
    public void review() {

    }

    // 강사 답글
    @PostMapping("{reviewId}")
    public void comment() {

    }
    // 답글 삭제
    @DeleteMapping("{reviewId}")
    public void delete() {

    }
    // 답글 수정
    @PutMapping("{reviewId}")
    public void path() {

    }
}
