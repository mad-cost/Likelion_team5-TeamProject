package com.example.homeGym.toss.service;


import com.example.homeGym.toss.dto.PaymentCancelDto;
import com.example.homeGym.toss.dto.PaymentConfirmDto;
import org.springframework.stereotype.Service;

@Service
public class TossHttpServiceImpl implements TossHttpService{
    @Override
    public Object confirmPayment(PaymentConfirmDto dto) {
        return dto;
    }

    @Override
    public Object getPayment(String paymentKey) {
        return paymentKey;
    }

    @Override
    public Object cancelPayment(String paymentKey, PaymentCancelDto cancelDto) {
        return cancelDto;
    }
}
