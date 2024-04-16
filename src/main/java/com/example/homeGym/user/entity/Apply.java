package com.example.homeGym.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Long userId;
    @Setter
    private Long programId;
    private Integer count;
    private String able_time;
    @Enumerated(EnumType.STRING)
    private ApplyState applyState;
    @CreationTimestamp
    private LocalDateTime createdAt;
    public enum ApplyState {
        STANDBY, // 신청 후 승인 대기
        FINISH
    }
}