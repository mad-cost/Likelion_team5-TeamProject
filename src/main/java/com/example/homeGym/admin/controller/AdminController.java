package com.example.homeGym.admin.controller;

import com.example.homeGym.auth.dto.SignInDto;
import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.repository.UserRepository;
import com.example.homeGym.user.service.ProgramServiceForUser;
import com.example.homeGym.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
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
  private final UserRepository userRepository;

  //회원가입 및 로그인
  @GetMapping("/signin")
  public String signUpAdminPage(){
    return "admin/admin-signin";
  }


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

//    user_program객체에서 userId를 통해 user_program의 Id값 가져오기 (State가 IN_PROGRESS값만 가져온다)
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

  //  유저 전체 환불
  @PostMapping("/user/{userId}/userProgram/{userProgramId}/allRefund")
  public String allRefund(
          @PathVariable("userId")
          Long userId,
          @PathVariable("userProgramId")
          Long userProgramId
  ){
    // 전액 환불
      userProgramService.allRefund(userProgramId);
      return "redirect:/admin/user/" + userId;

  }
  //    유저 회차 수정
  @PostMapping("/user/{userId}/userProgram/{userProgramId}/update")
  public String update(
          @PathVariable("userId")
          Long userId,
          @PathVariable("userProgramId")
          Long userProgramId,
          Model model
  ) {
      model.addAttribute("user", userService.findById(userId));
      model.addAttribute("userProgramId", userProgramId);

    //    user_program객체에서 userId를 통해 user_program의 Id값 가져오기 (State가 IN_PROGRESS값만 가져온다)
    List<Long> userProgramAll = userProgramService.findAllByUserIdConvertId(userId);
    //    user_program객체의 Id값을 형변환 Long -> UserProgramDto
    List<UserProgramDto> userPrograms = userProgramService.findByIds(userProgramAll);
    for (UserProgramDto dto : userPrograms) {
      Long programs = dto.getProgramId();
      dto.setProgram(programServiceForUser.findById(programs));
    }
    log.info("####### : {}", userPrograms.get(0).getId());
    model.addAttribute("userPrograms", userPrograms);

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

//  유저 회차 환불
  @PostMapping("/user/{userId}/userProgram/{userProgramId}/refund")
  public String refund(
          @PathVariable("userId")
          Long userId,
          @PathVariable("userProgramId")
          Long userProgramId
  ){
    log.info("@@@@ ; {}", userProgramId); //13
    userProgramService.refund(userProgramId);


    return "redirect:/admin/user/{userId}";
  }

}
