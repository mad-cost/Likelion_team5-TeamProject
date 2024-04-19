package com.example.homeGym.instructor.controller;

import com.example.homeGym.CustomInstructorDetails;
import com.example.homeGym.instructor.dto.InstructorCreateDto;
import com.example.homeGym.instructor.dto.InstructorProfileDto;
import com.example.homeGym.instructor.dto.InstructorWithdrawalDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repository.InstructorRepository;
import com.example.homeGym.instructor.service.InstructorService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;
    private final InstructorRepository instructorRepository;

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
    public String proposal(@ModelAttribute InstructorCreateDto instructorCreateDto) {
        log.info("Creating instructor with name: {}", instructorCreateDto.getName());
        instructorService.createInstructor(instructorCreateDto);
        return "redirect:/instructor/proposal/success";
    }
    @GetMapping("/proposal/success")
    public String proposalSuccessPage(){
        return "/instructor/proposal-success";
    }

    //로그인 아이디 중복 검사
    @PostMapping("/check-loginId")
    @ResponseBody
    public ResponseEntity<?> checkLoginId(@RequestBody Map<String, String> request) {
        String loginId = request.get("loginId");
        boolean isAvailable = instructorService.isLoginIdAvailable(loginId);
        return ResponseEntity.ok(Map.of("isAvailable", isAvailable));
    }
    // 이메일 중복 검사
    @PostMapping("/check-email")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean isAvailable = instructorService.isEmailAvailable(email);
        return ResponseEntity.ok(Map.of("isAvailable", isAvailable));
    }


    @GetMapping("/withdraw")
    public String showWithdrawForm(Model model) {
        model.addAttribute("withdrawal", new InstructorWithdrawalDto());
        return "/instructor/withdrawProposal";
    }


    @PostMapping("/withdraw")
    public String submitWithdrawForm(@ModelAttribute("withdrawal") InstructorWithdrawalDto withdrawalDto, Model model) {
        String message = instructorService.withdrawalProposal(1L, withdrawalDto.getWithdrawalReason());
        model.addAttribute("message", message);
        return "instructor/withdrawResult";
    }

    // 강사 페이지
    @GetMapping("/")
    public String InstructorPage(Model model) {


        model.addAttribute("profileDto", new InstructorProfileDto(
                //임시 데이터 넣기
                "/assets/img/free-icon-lion-512px.png", "정동은", 4.2));
        return "instructor/instructor-page";
    }

    //강사 정보 수정 페이지
    @GetMapping("/profile")
    public String profileUpdatePage(){
        return "instructor/instructor-update";
    }

    // 강사 정보 수정
    @PutMapping("/profile")
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
