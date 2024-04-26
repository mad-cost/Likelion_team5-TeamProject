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
@RequestMapping("/payments")
@RequiredArgsConstructor
public class TossPaymentController {
    private final OrderService service;

    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto
    ) {
        return service.confirmPayment(dto);
    }



}