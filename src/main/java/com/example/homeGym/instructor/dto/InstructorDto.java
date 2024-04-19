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
    private String name;
    private String loginId;
    private String password;
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
    private String account;

    public static InstructorDto fromEntity(Instructor entity) {

        return InstructorDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .loginId(entity.getLoginId())
                .password(entity.getPassword())
                .gender(entity.getGender())
                .birthyear(entity.getBirthyear())
                .birthday(entity.getBirthday())
                .state(entity.getState())
                .career(entity.getCareer())
                .profileImageUrl(entity.getProfileImageUrl().toString())
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
