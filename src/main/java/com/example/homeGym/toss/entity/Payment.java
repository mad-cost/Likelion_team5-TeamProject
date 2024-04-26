package com.example.homeGym.toss.entity;


import com.example.homeGym.toss.dto.PaymentConfirmDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Column(nullable = false, name = "pay_type")
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false, name = "order_name")
    private String orderName;

    @Column(nullable = false, name = "order_id")
    private String orderId;

    private boolean paySuccessYN;

    @Column(name = "user_id")
    @Setter
    private Long userId;

    @Column
    private String paymentKey;

    @Column
    private String failReason;

    @Column
    private boolean cancelYN;

    @Column
    private String cancelReason;

    @Column(name = "program_id")
    @Setter
    private Long programId;

    @CreationTimestamp
    private LocalDateTime createAt;

    public PaymentConfirmDto toPaymentResponseDto() {
        return PaymentConfirmDto.builder()
                .payType(payType.getPayTypeDescription())
                .amount(amount)
                .orderName(orderName)
                .orderId(orderId)
                .userId(userId)
                .createAt(String.valueOf(getCreateAt()))
                .cancelYN(cancelYN)
                .failReason(failReason)
                .build();
    }


}