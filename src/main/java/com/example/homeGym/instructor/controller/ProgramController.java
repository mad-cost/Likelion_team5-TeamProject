package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/program/{instructorId}")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    // 생성 페이지
    @GetMapping
    public void createPage() {

    }

    // 생성 요청
    @PostMapping
    public void requestCreate() {

    }

    // 수정 요청
    @PostMapping("/update")
    public void requestPath() {

    }
}
