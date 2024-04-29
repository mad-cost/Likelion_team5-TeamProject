package com.example.homeGym.order.controller;


import com.example.homeGym.order.dto.ProgramOrderDto;
import com.example.homeGym.order.service.OrderService;
import com.example.homeGym.toss.dto.PaymentCancelDto;
import com.example.homeGym.toss.entity.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;
  
    @GetMapping("/schedule")
    public String selectSchedulePage(){
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
   


    @GetMapping("{id}/payment")
    public Object readTossPayment(
            @PathVariable("id")
            Long id
    ) {
        return service.readTossPayment(id);
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
