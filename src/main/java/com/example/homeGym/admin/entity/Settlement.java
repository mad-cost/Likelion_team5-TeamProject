package com.example.homeGym.admin.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
// 정산 신청
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Long instructorId;
    @Setter
    private Integer amount; // 정산 받고싶은 금액
    @Setter
    @Enumerated(EnumType.STRING)
    private SettlementState state;
    @CreationTimestamp
    private LocalDateTime createdAt; // 정산 신청 날짜
    @Setter
    private LocalDateTime completeTime;
    @Setter
    private String dateCreateAt; // yyyy-mm-dd
    @Setter
    private String dateCompleteTime;
    public enum SettlementState{
        SETTLEMENT_PENDING, COMPLETE
    }
}