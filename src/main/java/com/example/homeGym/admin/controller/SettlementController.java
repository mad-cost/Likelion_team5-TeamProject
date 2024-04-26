package com.example.homeGym.admin.controller;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.admin.dto.SettlementFeeDto;
import com.example.homeGym.admin.service.SettlementService;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("instructor/settlement")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService service;
    private final AuthenticationFacade authenticationFacade;
    // 정산 페이지
    @GetMapping()
    public String settlementPage(Model model) {
        Instructor currentInstructor = authenticationFacade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        SettlementDto settlementDto = new SettlementDto();
        settlementDto.setInstructorId(currentInstructor.getId()); // SettlementDto에 강사 ID 설정

        SettlementFeeDto settlementFeeDto = service.settlementFeeFindByInstructorId(currentInstructor.getId());

        model.addAttribute("settlementDto", settlementDto);
        model.addAttribute("settlementFeeDto", settlementFeeDto);
        return "/instructor/settlement";

    }

    // 정산 요청
    @PostMapping()
    public String requestSettlement(SettlementDto dto, RedirectAttributes redirectAttributes) {
        try {
            service.settlementApply(dto);
        } catch (GlobalExceptionHandler e) {
            // 예외 발생 시, 메시지를 redirect attributes에 추가
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/instructor/settlement";
        }
        return "redirect:/instructor/";
    }
}
