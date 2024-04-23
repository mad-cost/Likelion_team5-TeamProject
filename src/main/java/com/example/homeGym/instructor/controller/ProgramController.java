package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    // 생성 페이지
    @GetMapping("/{instructorId}")
    public String createPage(
            @PathVariable("instructorId") Long instructorId,
            Model model
    ) {
        model.addAttribute("instructorId", instructorId);
        model.addAttribute("programDto", new ProgramDto());
        return "instructor-program"; // 생성 페이지의 뷰 이름
    }

    // 생성 요청
    @PostMapping("/{instructorId}")
    public String requestCreate(
            @PathVariable("instructorId") Long instructorId,
            @Valid @ModelAttribute ProgramDto programDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "instructor-program"; // 에러가 있으면 다시 생성 페이지로 이동
        }
        programService.createProgram(programDto);
        return "redirect:/program/" + instructorId; // 생성된 프로그램 목록 페이지로 리다이렉트
    }

    // 수정 요청
    @PostMapping("/{programId}/update")
    public String requestUpdate(
            @PathVariable("programId") Long programId,
            @Valid
            @ModelAttribute ProgramDto programDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "editProgram"; // 에러가 있으면 다시 수정 페이지로 이동
        }
        programService.updateProgram(programId, programDto);
        return "redirect:/program"; // 수정된 프로그램 목록 페이지로 리다이렉트
    }

    // 프로그램 삭제
    @DeleteMapping("/{programId}")
    public String deleteProgram(
            @PathVariable("programId") Long programId
    ) {
        programService.deleteProgram(programId);
        return "redirect:/program"; // 삭제 후 프로그램 목록 페이지로 리다이렉트
    }
}
