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

    @GetMapping("/{instructorId}")
    public String createPage(
            @PathVariable("instructorId") Long instructorId,
            Model model
    ) {
        model.addAttribute("instructorId", instructorId);
        model.addAttribute("programDto", new ProgramDto());
        return "/instructor/program/instructor-program";
    }

    @PostMapping("/{instructorId}")
    public String requestCreate(
            @PathVariable("instructorId") Long instructorId,
            @Valid @ModelAttribute ProgramDto programDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("instructorId", instructorId);
            return "/instructor/program/instructor-program";
        }
        try {
            Long programId = programService.createProgram(programDto);
            return "redirect:/program/instructor/" + instructorId + "/" + programId;
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
        ProgramDto programDto = programService.findByProgramId(List.of(programId)).get(0);
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
