package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String name;
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String birthyear;
    private String birthday;
    //회원가입 승인 전 x, 승인 후 ROLE_INSTRUCTOR
    @Setter
    private String roles;
    @Setter
    @Enumerated(EnumType.STRING)
    private InstructorState state;
    @Setter
    private String career;
    @Setter
    private String profileImageUrl;
    @Setter
    private String certificate;
    @Setter
    private String medal;
    @Setter
    private String email;
    @Setter
    private String phone;
    @Setter
    private String bank;
    @Setter
    private String bankName;
    @Setter
    private String account;
    @Setter
    private String withdrawalReason;
    @Setter
    private Double rating;   //별점 평점

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum InstructorState{
        REGISTRATION_PENDING, WITHDRAWAL_PENDING, ACTIVE, WITHDRAWAL_COMPLETE
    }

}