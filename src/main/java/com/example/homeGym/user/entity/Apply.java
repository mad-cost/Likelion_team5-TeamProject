package com.example.homeGym.user.entity;

import com.example.homeGym.instructor.entity.Time;
import com.example.homeGym.instructor.entity.Week;
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
    private Long userId;
    private Long programId;
    private Integer count;
    @Enumerated(EnumType.STRING)
    private Week able_week;
    @Enumerated(EnumType.STRING)
    private Time able_time;
    @Enumerated(EnumType.STRING)
    private ApplyState applyState;
    @CreationTimestamp
    private LocalDateTime createdAt;
    public enum ApplyState {
        APPLIED,    // 사용자가 신청을 완료했으나 결제는 완료되지 않은 상태
        PAID,       // 결제가 완료되었으나 강사의 승인을 기다리는 상태
        APPROVED    // 강사의 승인을 받고 참여 확정된 상태
    }
}