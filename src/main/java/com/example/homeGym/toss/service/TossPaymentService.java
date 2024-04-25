package com.example.homeGym.toss.service;


import com.example.homeGym.toss.entity.Payment;
import com.example.homeGym.toss.repo.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TossPaymentService {
    private final PaymentRepository paymentRepository;

    public Payment requestTossPayment(Payment payment, Long userId) {
        payment.setUserId(userId);

        return paymentRepository.save(payment);
    }



}