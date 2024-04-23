package com.example.homeGym.admin.controller;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/program")
public class AdProgramController {
  private final ProgramService programService;
  private final InstructorService instructorService;

  @GetMapping("/creation")
  public String creation(
          Model model
  ){
    List<Program> programs = programService.findAll();

//    Program의 state가 CREATION_PENDING 가져오기
    List<ProgramDto> stateCreates = programService.findAllByStateIsCreation(programs);
    log.info("@@@@@:{}",stateCreates.size());
    for (ProgramDto dto : stateCreates){
      Long InstructorId  = dto.getInstructorId();
      dto.setInstructor(instructorService.findByLongId(InstructorId));
    }
    model.addAttribute("programs", stateCreates);
    return "/admin/creation";
  }
//  신규 프로그램 등록
  @PostMapping("/{programId}/creation")
  public String newCreation(
          @PathVariable("programId")
          Long programId
  ){
    programService.stateConvertInProgress(programId);
    return "redirect:/admin/program/creation";
  }
//  신규 프로그램 등록 거절
  @PostMapping("/{programId}/delete")
  public String deleteCreation(
          @PathVariable("programId")
          Long programId
  ){
    programService.deleteInProgress(programId);
    return "redirect:/admin/program/creation";
  }


}
