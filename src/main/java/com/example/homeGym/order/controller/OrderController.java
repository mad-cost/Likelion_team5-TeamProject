package com.example.homeGym.order.controller;


import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.common.util.AuthenticationUtilService;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.order.dto.ProgramOrderDto;
import com.example.homeGym.order.service.OrderService;
import com.example.homeGym.toss.dto.PaymentCancelDto;
import com.example.homeGym.toss.entity.Payment;
import com.example.homeGym.user.dto.ApplyDto;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
    private final ProgramService programService;
    private final AuthenticationUtilService authenticationUtilService;
    private final UserService userService;
  
    @GetMapping("/{programId}/schedule")
    public String selectSchedulePage(
            @PathVariable("programId") Long programId,
            Model model,
            Authentication authentication
    ){
        Long userId = authenticationUtilService.getId(authentication);

        //유저 정보
        UserDto userDto = userService.findById(userId);
        Program program = programService.findById(programId);
        model.addAttribute("program", program);
        model.addAttribute("user", userDto);
        return "order/select-schedule";
    }

    @GetMapping
    public List<ProgramOrderDto> readAll() {
        return service.readAll();
    }

    @GetMapping("{id}")
    public ProgramOrderDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return service.readOne(id);
    }

    @GetMapping("/payment")
    public ModelAndView readTossPayment() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("orderId", "abcddksdkf2203");
        mav.addObject("amount", "15000");
        mav.addObject("orderName", "김건강씨의 헬스 프로젝트");
        mav.addObject("userName", "이시은");
        mav.addObject("userEmail", "sieun@naver.com");
        mav.addObject("userPhone", "01011111111");
        mav.setViewName("order/payment");

        return mav;
    }

    @GetMapping("/success")
    public String success() {
        return "order/success";
    }

    @GetMapping("/fail")
    public ModelAndView fail(@RequestParam(value = "message") final String message, @RequestParam(value = "code")final String code) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", message);
        mav.addObject("code", code);
        mav.setViewName("order/fail");
        return mav;
    }

    @PostMapping("{id}/cancel")
    public Object cancelPayment(
            @PathVariable("id")
            Long id,
            @RequestBody
            PaymentCancelDto dto
    ) {
        return service.cancelPayment(id, dto);
    }

}
