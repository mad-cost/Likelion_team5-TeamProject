package com.example.homeGym.admin.controller;


import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/instructor")
public class AdminInstController {
  private final InstructorService instructorService;
  private final ProgramService programService;

  @GetMapping("/{instructorId}")
  public String instructorId(
          @PathVariable("instructorId")
          Long instructorId,
          Model model
  ){
    model.addAttribute("instructor", instructorService.findById(instructorId));

//    List<ProgramDto> programs = programService.findAllByInstructorIdConvertProgramId(instructorId);
//    model.addAttribute("programs", programs);

    return "/admin/instructor";
  }
}
