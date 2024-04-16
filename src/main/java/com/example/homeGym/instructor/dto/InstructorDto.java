package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Gender;
import com.example.homeGym.instructor.entity.Instructor;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDto {
    private Long id;
    private String loginId;
    private String password;
    private String name;
    private Gender gender;
    private String birthyear;
    private String birthday;
    private Instructor.InstructorState state;
    private String career;
    private String profileImageUrl;
    private String certificate;
    private String medal;
    private String email;
    private String phone;
    private String bank;
    private String bankName;
    private Integer account;

    public static InstructorDto fromEntity(Instructor entity) {

        return InstructorDto.builder()
                .id(entity.getId())
                .loginId(entity.getLoginId())
                .password(entity.getPassword())
                .name(entity.getName())
                .gender(entity.getGender())
                .career(entity.getCareer())
                .profileImageUrl(entity.getProfileImageUrl())
                .certificate(entity.getCertificate())
                .medal(entity.getMedal())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .bank(entity.getBank())
                .bankName(entity.getBankName())
                .account(entity.getAccount())
                .build();
    }
}
