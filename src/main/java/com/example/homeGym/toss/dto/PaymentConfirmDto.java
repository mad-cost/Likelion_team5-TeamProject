package com.example.homeGym.toss.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmDto {
    private String payType;
    private Long amount;
    private String orderName;
    private String orderId;
    private Long userId;
    private String successUrl;
    private String failUrl;
    private String paymentKey;

    private String failReason;
    private boolean cancelYN;
    private String cancelReason;
    private String createAt;
}
