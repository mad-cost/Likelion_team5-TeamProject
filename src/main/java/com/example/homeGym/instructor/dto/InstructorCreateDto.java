package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Gender;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCreateDto {
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
    private Integer account;

    public static InstructorCreateDto fromEntity(Instructor entity) {

        return InstructorCreateDto.builder()
                .id(entity.getId())
                .loginId(entity.getLoginId())
                .password(entity.getPassword())
                .name(entity.getName())
                .gender(entity.getGender())
                .birthyear(entity.getBirthyear())
                .birthday(entity.getBirthday())
                .career(entity.getCareer())
                .profileImageUrl(entity.getProfileImageUrl())
                .certificate(entity.getCertificate())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .bank(entity.getBank())
                .bankName(entity.getBankName())
                .account(entity.getAccount())
                .build();
    }
    public Instructor toEntity(){
        return Instructor.builder()
                .id(this.id)
                .loginId(this.loginId)
                .password(this.password) // TODO: 암호화 필요
                .name(this.name)
                .gender(this.gender)
                .birthyear(this.birthyear)
                .birthday(this.birthday)
                .state(Instructor.InstructorState.REGISTRATION_PENDING)
                .career(this.career)
                .profileImageUrl(this.profileImageUrl)
                .certificate(this.certificate)
                .email(this.email)
                .phone(this.phone)
                .bank(this.bank)
                .bankName(this.bankName)
                .account(this.account)
                .build();
    }
}
