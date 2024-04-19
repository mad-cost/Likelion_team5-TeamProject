package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    // 생성 페이지
    @GetMapping("{instructorId}")
    public void createPage(
            @PathVariable("instructorId")
            Long instructorId
    ) {

    }

    // 생성 요청
    @PostMapping("{instructorId}")
    public void requestCreate(
            @PathVariable("instructorId")
            Long instructorId
    ) {

    }

    // 수정 요청
    @PostMapping("/{programId}/update")
    public ProgramDto requestUpdate(
            @PathVariable("programId")
            Long programId
    ) {
        return null;
    }

    // 프로그램 승인 대기 삭제
    @DeleteMapping("/{programId}")
    public void deleteProgram(
            @PathVariable("programId")
            Long programId
    ) {

    }
}
