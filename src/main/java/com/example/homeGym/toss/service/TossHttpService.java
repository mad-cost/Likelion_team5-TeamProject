package com.example.homeGym.toss.service;


import com.example.homeGym.toss.dto.PaymentResponseDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("tossPayments")
public interface TossHttpService {
    @PostExchange("/response")
    Object responsePayment(
            @RequestBody
            PaymentResponseDto dto
    );

    @GetExchange("/{paymentKey}")
    Object getPayment(
            @PathVariable("paymentKey")
            String paymentKey
    );

}
