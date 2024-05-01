package com.example.homeGym.toss.controller;


import com.example.homeGym.order.service.OrderService;
import com.example.homeGym.toss.dto.PaymentConfirmDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossPaymentController {
    private final OrderService service;

    @PostMapping("/payments")
    public void payments() {

    }

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto
    ) {
        System.out.println(dto.getPaymentKey());
        System.out.println(dto.getOrderId());
        System.out.println(dto.getAmount());
        return service.confirmPayment(dto);
    }



}