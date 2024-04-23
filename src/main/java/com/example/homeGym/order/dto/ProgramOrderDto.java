package com.example.homeGym.order.dto;

import com.example.homeGym.order.entity.ProgramOrder;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramOrderDto {
    private Long id;
    private Long userId;

    private Long programId;

    private String programTitle;

    private Long amount;

    private String tossPaymentKey;
    private String tossOrderId;

    public static ProgramOrderDto fromEntity(ProgramOrder order) {
        return ProgramOrderDto.builder()
                .id(order.getOrderId())
                .programTitle(order.getProgram().getTitle())
                .userId(order.getUser().getId())
                .programId(order.getProgram().getId())
                .amount(order.getAmount())
                .tossPaymentKey(order.getTossPaymentKey())
                .tossOrderId(order.getTossOrderId())
                .build();
    }


}
