package com.example.homeGym.toss;

import com.example.homeGym.toss.config.TossPaymentConfig;
import com.example.homeGym.toss.dto.PaymentConfirmDto;
import com.example.homeGym.toss.dto.PaymentDto;
import com.example.homeGym.toss.service.TossPaymentService;
import com.example.homeGym.user.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/payments")
public class TossPaymentController {
    private final TossPaymentService paymentService;
    private final TossPaymentConfig tossPaymentConfig;


    public TossPaymentController(
            TossPaymentService paymentService,
            TossPaymentConfig tossPaymentConfig
    ) {
        this.paymentService = paymentService;
        this.tossPaymentConfig = tossPaymentConfig;
    }

    @PostMapping("/toss")
    public ResponseEntity requestTossPayment(
            @AuthenticationPrincipal
            User principal,
            @RequestBody
            @Validated
            PaymentDto paymentDto
    ) {
        PaymentConfirmDto confirmDto
                = paymentService.requestTossPayment(
                paymentDto.toEntity(),
                principal.getId()).toPaymentResponseDto();
        paymentDto.setSuccessfulUrl(paymentDto.getSuccessfulUrl());
        paymentDto.setFailUrl(paymentDto.getFailUrl());

        return ResponseEntity.ok().body(new PaymentConfirmDto());
    }


}