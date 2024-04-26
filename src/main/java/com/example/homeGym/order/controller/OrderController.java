package com.example.homeGym.order.controller;


import com.example.homeGym.order.service.OrderService;
import com.example.homeGym.toss.dto.PaymentCancelDto;
import com.example.homeGym.toss.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;


    @GetMapping("/orders/{id}/payment")
    public Object readTossPayment(
            @PathVariable("id")
            Long id
    ) {
        return service.readTossPayment(id);
    }

    @PostMapping("/orders/{id}/cancel")
    public Object cancelPayment(
            @PathVariable("id")
            Long id,
            @RequestBody
            PaymentCancelDto dto
    ) {
        return service.cancelPayment(id, dto);
    }


}
