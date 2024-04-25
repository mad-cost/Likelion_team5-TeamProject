package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@ToString
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
    private String email;
    @Setter
    private String name;
    @Setter
    private String password;
    @Setter
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Setter
    private String birthyear;
    @Setter
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

    // 비밀번호 설정 메서드
    public void setPassword(String password, PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public enum InstructorState{
        REGISTRATION_PENDING, // 신청 대기중인 강사
        WITHDRAWAL_PENDING, // 탈퇴 신청 대기중인 강사
        ACTIVE, // 활동중인 강사
        WITHDRAWAL_COMPLETE // 탈퇴 신청 완료된 강사
    }

}