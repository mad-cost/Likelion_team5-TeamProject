package com.example.homeGym.admin.controller;

import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.service.ProgramServiceForUser;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;
  private final UserProgramService userProgramService;
  private final ProgramServiceForUser programServiceForUser;
  private final InstructorService instructorService;

  @GetMapping
  public String admin(
          Model model
  ) {
    model.addAttribute("users", userService.findAllByOrderByName());
    model.addAttribute("instructors", instructorService.findAllByOrderByName());
    return "admin/admin";
  }

  //   유저 상세 페이지
  @GetMapping("/user/{userId}")
  public String userId(
          @PathVariable("userId")
          Long userId,
          Model model
  ) {
//    user객체에서 Id가져오기
    model.addAttribute("user", userService.findById(userId));

//    user_program객체에서 userId를 통해 user_program의 Id값 가져오기
    List<Long> userProgramsId = userProgramService.findAllByUserIdConvertId(userId);
//    user_program객체의 Id값을 형변환 Long -> UserProgramDto
    List<UserProgramDto> userPrograms = userProgramService.findByIds(userProgramsId);
//    log.info("userProgramsInfo : {}", userPrograms);
    for (UserProgramDto dto : userPrograms) {
      Long programId = dto.getProgramId();
      dto.setProgram(programServiceForUser.findById(programId));
    }
    model.addAttribute("userPrograms", userPrograms);

    return "admin/user";
  }

  //  유저 삭제
  @PostMapping("/user/{userId}/program/{programId}")
  public String userProgramDelete(
          @PathVariable("userId")
          Long userId,
          @PathVariable("programId")
          Long programId
  ) {
    //    user_program객체에서 userId를 통해 user_program의 Id값 가져오기
    List<Long> userProgramsId = userProgramService.findAllByUserIdConvertId(userId);
    userProgramService.deleteByProgram(userProgramsId, programId);
    return "redirect:/admin/user/{userId}";
  }

  //  유저 수정
  @PostMapping("/user/{userId}/update/program/{programId}")
  public String userProgramUpdate(
          @PathVariable("userId")
          Long userId,
          @PathVariable("programId")
          Long programId,
          Model model
  ) {
    //    user객체에서 Id가져오기
    model.addAttribute("user", userService.findById(userId));
    //    user_program객체에서 userId를 통해 user_program의 Id값 가져오기
    List<Long> userProgramsId = userProgramService.findAllByUserIdConvertId(userId);
    //    user_program객체의 Id값을 형변환 Long -> UserProgramDto
    List<UserProgramDto> userPrograms = userProgramService.findByIds(userProgramsId);
    for (UserProgramDto dto : userPrograms) {
      Long programs = dto.getProgramId();
      dto.setProgram(programServiceForUser.findById(programs));
    }
    model.addAttribute("userPrograms", userPrograms);

    model.addAttribute("programId", programId);
    return "/admin/userUpdate";
  }

  // 카운트 수정 및 redirect
  @PostMapping("/user/{userId}/updated/program/{programId}")
  public String userProgramUpdated(
          @PathVariable("userId")
          Long userId,
          @PathVariable("programId")
          Long programId,
          @RequestParam
          Integer count
  ) {
    List<Long> userProgramsId = userProgramService.findAllByUserIdConvertId(userId);
    userProgramService.userCountUpdate(userProgramsId, programId, count);
    return "redirect:/admin/user/{userId}";
  }
}
