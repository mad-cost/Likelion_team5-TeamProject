package com.example.homeGym.instructor.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("instructor/{instructorId}/settlement")
@RequiredArgsConstructor
public class SettlementController {

    // 정산 페이지
    @GetMapping()
    public void settlementPage() {

    }

    // 정산 요청
    @PostMapping()
    public void requestSettlement() {

    }
}
