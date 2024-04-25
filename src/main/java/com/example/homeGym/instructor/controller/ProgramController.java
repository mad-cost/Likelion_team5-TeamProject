package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.service.ProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/program")
@RequiredArgsConstructor
public class ProgramController {
    private final ProgramService programService;

    @GetMapping()
    public String createPage(
            Model model
    ) {
        model.addAttribute("programDto", new ProgramDto());
        return "/instructor/program/instructor-program";
    }

    @PostMapping()
    public String requestCreate(
            @Valid @ModelAttribute ProgramDto programDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/instructor/program";
        }
        try {
            programService.createProgram(programDto);
            return "redirect:/instructor/program";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page"; // 에러 페이지로 리다이렉트
        }
    }


    @GetMapping("/update/{programId}")
    public String updateProgram(
            @PathVariable("programId") Long programId,
            Model model
    ) {
        ProgramDto programDto = programService.findByProgramId(programId);
        model.addAttribute("programDto", programDto);
        return "/instructor/program/update-program";
    }

    @PostMapping("/update/{programId}")
    public String requestUpdate(
            @PathVariable("programId") Long programId,
            @Valid @ModelAttribute ProgramDto programDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "/instructor/program/update-program";
        }
        try {
            programService.updateProgram(programId, programDto);
            return "redirect:/program";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page"; // 에러 페이지로 리다이렉트
        }
    }

    @DeleteMapping("/{programId}")
    public String deleteProgram(
            @PathVariable("programId") Long programId,
            Model model
    ) {
        try {
            programService.deleteProgram(programId);
            return "redirect:/program";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error-page"; // 에러 페이지로 리다이렉트
        }
    }
}
