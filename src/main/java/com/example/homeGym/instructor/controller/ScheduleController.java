package com.example.homeGym.instructor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("instructor/{instructorId}/schedule")
@RequiredArgsConstructor
public class ScheduleController {

    // 강사 스케쥴
    @GetMapping()
    public void schedule() {

    }

    // 스케줄 생성
    @PostMapping()
    public void createSchedule() {

    }

    // 스켸줄 수정
    @PutMapping()
    public void pathSchedule() {

    }

    // 스케줄 삭제
    @DeleteMapping()
    public void deleteSchedule() {

    }
}
