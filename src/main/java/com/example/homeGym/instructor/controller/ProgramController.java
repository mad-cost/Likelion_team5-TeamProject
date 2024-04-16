package com.example.homeGym.instructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/program/{instructorId}")
@RequiredArgsConstructor
public class ProgramController {

    // 생성 페이지
    @GetMapping
    public void createPage() {

    }

    // 수정 요청
    @PostMapping("/update")
    public void requestPath() {

    }

    // 생성 요청
    @PostMapping
    public void requestCreate() {

    }
}
