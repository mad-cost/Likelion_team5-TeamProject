package com.example.homeGym.admin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
// 강사의 남은 정산금
public class SettlementFee {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Integer currentFee; // 정산 가능한 금액 즉, currentFee(300) - amount(100) = currentFee(200)
    @Setter
    private Integer totalFee; // 강사가 벌은 총 금액
    @Setter
    private Long instructorId;
    @CreationTimestamp
    private LocalDateTime createdAt;
}