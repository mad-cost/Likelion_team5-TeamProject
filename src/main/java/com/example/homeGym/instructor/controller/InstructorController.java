package com.example.homeGym.instructor.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("instructor")
@RequiredArgsConstructor
public class InstructorController {

    // 강사 로그인
    @PostMapping("/login")
    public void login() {

    }

    // 강사 로그아웃
    @PostMapping("/logout")
    public void logout() {

    }

    // 강사 신청
    @PostMapping("/proposal")
    public void proposal() {

    }

    // 강사 회원탈퇴
    @PostMapping("/{instructorId}/withdraw")
    public void withdraw() {

    }

    // 강사 페이지
    @GetMapping("/{instructorId}")
    public void InstructorPage() {

    }

    // 강사 정보 수정
    @PutMapping("/instructorId}/profile")
    public void profile() {

    }

    // 강사 프로그램 상세
    @GetMapping("/{instructorId}/{programId}")
    public void InstrutcorProgramList() {

    }

    // 강사 프로그램 회원 상세
    @PostMapping("/{instructorId}/program/{programId}/user/{userId}")
    public void userProgramList() {

    }

    // 강사 일지 작성
    @PostMapping("/{instructorId}/program/{programId}/user/{userId}")
    public void createDaily() {

    }

    // 강사 일지 수정
    @PutMapping("/{instructorId}/program/{programId}/user/{userId}")
    public void pathDaily() {

    }
}
