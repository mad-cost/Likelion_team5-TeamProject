package com.example.homeGym.admin.entity;

import jakarta.persistence.*;
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
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Long instructorId;
    @Setter
    private Integer amount;
    @Setter
    @Enumerated(EnumType.STRING)
    private SettlementState state;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime completeTime;
    public enum SettlementState{
        SETTLEMENT_PENDING, COMPLETE
    }
}