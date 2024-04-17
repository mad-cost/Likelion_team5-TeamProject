//package com.example.homeGym.toss;
//
//import com.example.homeGym.toss.config.TossPaymentConfig;
//import com.example.homeGym.toss.dto.PaymentDto;
//import com.example.homeGym.toss.dto.PaymentResponseDto;
//import com.example.homeGym.user.entity.User;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@Validated
//@RequestMapping("/payments")
//public class TossPaymentController {
//    private final TossPaymentConfig tossPaymentConfig;
//
//    public TossPaymentController(TossPaymentConfig tossPaymentConfig) {
//        this.tossPaymentConfig = tossPaymentConfig;
//    }
//
//    @PostMapping("/toss")
//    public ResponseEntity requestTossPayment(
//            @AuthenticationPrincipal
//            User principal,
//            @RequestBody
//            @Validated
//            PaymentDto paymentDto
//            ) {
//        return null;
//    }
//
//
//}
