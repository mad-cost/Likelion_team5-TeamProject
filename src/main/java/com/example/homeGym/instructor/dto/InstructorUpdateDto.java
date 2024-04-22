package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Gender;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorUpdateDto {
    private Long id;
    private String loginId;
    private String password;
    private String passwordCheck;
    private String name;
    private Gender gender;
    private String birthyear;
    private String birthday;
    private String state;
    private String career;
    private String profileImageUrl;
    private String certificate;
    private String email;
    private String phone;
    private String bank;
    private String bankName;
    private String account;
}
