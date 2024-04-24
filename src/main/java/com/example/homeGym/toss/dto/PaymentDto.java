package com.example.homeGym.toss.dto;


import com.example.homeGym.toss.entity.PayType;
import com.example.homeGym.toss.entity.Payment;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    // 결제 수단 (카드/현금/포인트)
    @NonNull
    private PayType payType;

    // 금액
    @NonNull
    private Long amount;

    // 결제 프로그램 이름
    @NonNull
    private String orderName;


    // 결제 성공 url
    private String successfulUrl;
    // 결제 실패 url
    private String failUrl;

    public Payment toEntity() {
        return Payment.builder()
                .payType(payType)
                .amount(amount)
                .orderName(orderName)
                .orderId(UUID.randomUUID().toString())
                .paySuccessYN(false)
                .build();
    }
}