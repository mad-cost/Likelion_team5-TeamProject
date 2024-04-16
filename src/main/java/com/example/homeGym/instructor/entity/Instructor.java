package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String loginId;
    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    private InstructorState state;
    @Setter
    private String career;
    @Setter
    private String profileImageUrl;
    private String certificate;
    private String medal;
    private String email;
    @Setter
    private String phone;
    @Setter
    private String bank;
    @Setter
    private String bankName;
    private Integer account;
    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum InstructorState{
        REGISTRATION_PENDING, WITHDRAWAL_PENDING, ACTIVE, WITHDRAWAL_COMPLETE
    }

}