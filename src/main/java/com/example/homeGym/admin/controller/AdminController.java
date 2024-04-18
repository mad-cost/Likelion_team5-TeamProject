package com.example.homeGym.admin.controller;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.service.UserService;
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
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;
  private final UserProgramService userProgramService;
  private final ProgramService programService;


  @GetMapping
  public String admin(
          Model model
  ){
    model.addAttribute("users", userService.findAllByOrderByName());
    return "admin/admin";
  }

  @GetMapping("/user/{userId}")
  public String userId(
          @PathVariable("userId")
          Long userId,
          Model model
  ){
    model.addAttribute("user", userService.findById(userId));
    List<UserProgramDto> userPrograms = userProgramService.findAllByUserIdDto(userId);
    log.info("@@Size : {}", userPrograms.size());
    log.info("userPogramsId:{}, userPogramsId:{}", userPrograms.get(0).getId(),userPrograms.get(1).getId());
    model.addAttribute("userPrograms", userPrograms);


//    userProgram에서 userId가 {userId}인 programId가져오기
    List<Long> userProgramProgramId = userProgramService.findAllByUserId(userId);
    log.info("@@Size:{}", userProgramProgramId.size());
    log.info("userProgramId : {}, userProgramId : {}",userProgramProgramId.get(0), userProgramProgramId.get(1));
    List<ProgramDto> programs = programService.findByProgramId(userProgramProgramId);
    log.info("@@ProgramsSize : {}",programs.size());
    log.info("ProgramId : {}, ProgramId : {}", programs.get(0).getTitle(), programs.get(1).getTitle());

/* 추가사항
    userProgramService.findAllByUserId
    userProgramRepository.findAllByUserId
    ProgramService, repo, dto
 */
    return "admin/user";
  }


}
