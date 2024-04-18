package com.example.homeGym.user.controller;

import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.service.InstructorServiceForUser;
import com.example.homeGym.user.service.ProgramServiceForUser;
import com.example.homeGym.user.service.ReviewService;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
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

    @GetMapping("/loginpage")
    public String loginPage(){
        return "user/loginPage";
    }


    @GetMapping("/{userId}/mypage")
    public String myPage(
            @PathVariable("userId")
            Long userId,
            Model model
    ){
        UserDto userDto = new UserDto();
        //유저 정보
        userDto = userService.findById(userId);

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

    @GetMapping("/{userId}/program/{userProgramId}")
    @ResponseBody
    public String userProgramDetail(
            @PathVariable("userId")
            Long userId,
            @PathVariable("userProgramId")
            Long userProgramId,
            Model model
    ){
        UserProgramDto userProgramDto = new UserProgramDto();
        //유저 프로그램의 정보 하나를 가져온다
        userProgramDto = userProgramService.findById(userProgramId);

        //프로그램 정보를 가져온다
        userProgramDto.setProgram(programServiceForUser.findById(userProgramDto.getProgramId()));
        //강사 정보를 가져온다
        userProgramDto.setInstructor(instructorServiceForUser.findById(userProgramDto.getProgram().getInstructorId()));

        //내가 이 프로그램에 작성한 후기 가져오기
        ReviewDto reviewDto = new ReviewDto();

        //TODO 후기 뭔가 이상해서 나중에 수정 예정
        if (reviewService.findByUserProgramIdAndUserId(userProgramId, 1L) == null){
            model.addAttribute("reviews", reviewDto);
        }else {
            reviewDto = reviewService.findByUserProgramIdAndUserId(userProgramId, 1L);
        }

        model.addAttribute("reviews", reviewDto);
        System.out.println("reviewDto = " + reviewDto);
        model.addAttribute("program", userProgramDto);

        return "test";
    }
}
