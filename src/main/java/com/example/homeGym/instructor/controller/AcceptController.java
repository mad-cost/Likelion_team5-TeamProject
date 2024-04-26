package com.example.homeGym.instructor.controller;

import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.instructor.service.AcceptService;
import com.example.homeGym.user.dto.ApplyDto;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("instructor/accept")
@RequiredArgsConstructor
public class AcceptController {
    private final AcceptService acceptService;
    private final UserService userService;
    private final ProgramRepository programRepository;
    private final AuthenticationFacade facade;

    // 강사 수락 페이지
    @GetMapping
    public String acceptPage(Model model) {
        Instructor instructor = facade.getCurrentInstructor();
        List<ApplyDto> applyDtos = acceptService.readAllApplyUser(instructor.getId());
        model.addAttribute("applyDtos", applyDtos);
        return "instructor/accept";
    }

    // 수락하기
    @PostMapping("/{applyId}")
    public String accept(@PathVariable("applyId") Long applyId) {
        acceptService.accept(applyId);
        return "redirect:/instructor/accept";
    }

    // 거절하기
    @DeleteMapping("/{applyId}")
    public String deny(@PathVariable("applyId") Long applyId) {
        acceptService.deny(applyId);
        return "redirect:/instructor/accept";
    }
}
