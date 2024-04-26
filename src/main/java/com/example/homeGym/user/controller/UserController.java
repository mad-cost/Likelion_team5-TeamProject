package com.example.homeGym.user.controller;

import com.example.homeGym.common.util.AuthenticationUtilService;
import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.service.InstructorServiceForUser;
import com.example.homeGym.user.service.ProgramServiceForUser;
import com.example.homeGym.user.service.ReviewService;
import com.example.homeGym.user.service.UserService;
import com.example.homeGym.user.utils.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {
    private final UserService userService;
    private final UserProgramService userProgramService;
    private final ReviewService reviewService;
    private final ProgramServiceForUser programServiceForUser;
    private final InstructorServiceForUser instructorServiceForUser;
    private final AuthenticationUtilService authenticationUtilService;
    private final EmailService emailService;

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

    @PostMapping

    @GetMapping("/loginpage")
    public String loginPage(){
        return "user/loginPage";
    }


    @GetMapping("/mypage")
    public String myPage(
            Model model,
            Authentication authentication
    ){
        Long userId = authenticationUtilService.getId(authentication);

        //유저 정보
        UserDto userDto = userService.findById(userId);

        //유저가 진행중인 수업
        List<UserProgramDto> inProgressList = userProgramService.findByUserIdAndStateInProgress(userId);
//        System.out.println("inProgressList = " + inProgressList);
        //dto에 program정보 및 강사 정보 저장
        for (UserProgramDto programDto : inProgressList) {
            long programId = programDto.getProgramId();
            programDto.setProgram(programServiceForUser.findById(programId));
            long instructorId = programDto.getProgram().getInstructorId();
            programDto.setInstructor(instructorServiceForUser.findById(instructorId));
        }

        //유저의 종료된 수업
        List<UserProgramDto> finishList = userProgramService.findByUserIdAndStateFINISH(userId);
        //dto에 program정보 및 강사 정보 저장
        for (UserProgramDto userProgramDto : finishList) {
            long programId = userProgramDto.getProgramId();
            userProgramDto.setProgram(programServiceForUser.findById(programId));
            long instructorId = userProgramDto.getProgram().getInstructorId();
            userProgramDto.setInstructor(instructorServiceForUser.findById(instructorId));

        }

        model.addAttribute("userInfo", userDto);
        model.addAttribute("inProgress", inProgressList);
        model.addAttribute("finishProgress", finishList);


        return "user/myPage";
    }

    @GetMapping("/program/{userProgramId}")
    public String userProgramDetail(
            @PathVariable("userProgramId")
            Long userProgramId,
            Model model,
            Authentication authentication
    ){
        Long userId = authenticationUtilService.getId(authentication);
        new UserProgramDto();
        UserProgramDto userProgramDto = userProgramService.findById(userProgramId);
        //유저 프로그램의 정보 하나를 가져온다

        //프로그램 정보를 가져온다
        userProgramDto.setProgram(programServiceForUser.findById(userProgramDto.getProgramId()));

        //강사 정보를 가져온다
        userProgramDto.setInstructor(instructorServiceForUser.findById(userProgramDto.getProgram().getInstructorId()));

        //리뷰 가져오기
        List<ReviewDto> reviewDtos = reviewService.findByUserProgramIdAndUserId(userProgramId, userId);

        model.addAttribute("reviews", reviewDtos);
        model.addAttribute("program", userProgramDto);

        return "user/myDetail";
    }

    @GetMapping("mail")
    public void mailTest(){
        emailService.sendMail();
    }
}
