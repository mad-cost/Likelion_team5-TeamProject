package com.example.homeGym.instructor.controller;

import com.example.homeGym.CustomInstructorDetails;
import com.example.homeGym.instructor.dto.InstructorCreateDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.service.InstructorService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
@RequestMapping("instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    //인증쪽에서 작성
   /* // 강사 로그인
    @GetMapping("/login")
    public String loginPage(){
        return "instructor-login";
    }

    @PostMapping("/login")
    public void login() {
    }

    // 강사 로그아웃
    @PostMapping("/logout")
    public void logout() {
    }*/


    // 강사 신청
    @GetMapping("/proposal")
    public String proposalPage(Model model){
        model.addAttribute("InstructorCreateDto", new InstructorCreateDto());
        return "/instructor/proposal";
    }
    @PostMapping("/proposal")
    public void proposal(InstructorCreateDto instructorCreateDto) {
        instructorService.createInstructor(instructorCreateDto);
    }

    @GetMapping("/withdraw")
    public String withdrawPage(){
        return "/instructor/withdrawProposal";
    }


    @PostMapping("/withdraw")
    @ResponseBody  // Ajax 요청에 적합하게 JSON 응답을 반환하도록 수정
    public ResponseEntity<?> withdraw() {
        // 임시로 ID를 설정. 인증된 사용자의 ID를 사용하려면 주석 처리된 코드를 활성화
        Long instructorId = 1L; // 테스트용 1번 ID

        String resultMessage = instructorService.withdrawalProposal(instructorId);
        return ResponseEntity.ok(Collections.singletonMap("message", resultMessage));
    }

    // 강사 페이지
    @GetMapping("/{instructorId}")
    public void InstructorPage(
            @PathVariable("instructorId") Long instructorId
    ) {

    }

    // 강사 정보 수정
    @PutMapping("/{instructorId}/profile")
    public void profile(
            @PathVariable("instructorId") Long instructorId
    ) {

    }

    // 강사 후기 페이지
    @PostMapping("/{instructorId}/review")
    public String ViewReview() {
        return null;
    }

    // 강사 프로그램 상세
    @GetMapping("/{instructorId}/{programId}")
    public void InstrutcorProgramList(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("programId") Long programId
    ) {

    }

    // 강사 프로그램 회원 상세
    @GetMapping("/{instructorId}/program/{programId}/user/{userId}")
    public void userProgramList(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("programId") Long programId,
            @PathVariable("userId") Long userId
    ) {

    }

    // 강사 일지 작성
    @PostMapping("/{instructorId}/program/{programId}/user/{userId}")
    public void createDaily(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("programId") Long programId,
            @PathVariable("userId") Long userId
    ) {

    }

    // 강사 일지 수정
    @PutMapping("/{instructorId}/program/{programId}/user/{userId}")
    public void pathDaily(
            @PathVariable("instructorId") Long instructorId,
            @PathVariable("programId") Long programId,
            @PathVariable("userId") Long userId
    ) {

    }
}
