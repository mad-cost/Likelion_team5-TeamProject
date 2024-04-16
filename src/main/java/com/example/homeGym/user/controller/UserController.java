package com.example.homeGym.user.controller;

import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.dto.UserDto;
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
        //후에 dto에 progrm정보 저장
//        for (int i = 0; i < inProgressList.size(); i++) {
//            inProgressList.get(i).setProgram();
//        }

        //유저의 종료된 수업
        List<UserProgramDto> finishList = userProgramService.findByUserIdAndStateFINISH(userId);

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
            Long userProgramId
    ){
        UserProgramDto userProgramDto = new UserProgramDto();
        //유저 프로그램의 정보 하나를 가져온다
        userProgramDto = userProgramService.findById(userProgramId);

        //프로그램 정보를 가져온다
//        programService.findById(userProgramDto.getProgramId());

        //내가 이 프로그램에 작성한 후기 가져오기
        ReviewDto reviewDto = new ReviewDto();
        reviewDto = reviewService.findByIdAndUserId(userProgramId, userId);
        System.out.println(reviewDto);

        return "test";
    }
}
