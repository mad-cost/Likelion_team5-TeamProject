package com.example.homeGym.instructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("instructor/{instructorId}/accept")
@RequiredArgsConstructor
public class AcceptController {

    // 강사 수락 페이지
    @GetMapping
    public void acceptPage() {

    }

    // 수락하기
    @PostMapping
    public void accept() {

    }

    // 거절하기
    @DeleteMapping
    public void deny() {

    }
}
