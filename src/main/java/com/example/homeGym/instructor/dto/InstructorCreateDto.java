package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Gender;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorCreateDto {
    private Long id;
    private String email;
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
    private String phone;
    private String bank;
    private String bankName;
    private String account;

    public static InstructorCreateDto fromEntity(Instructor entity) {

        return InstructorCreateDto.builder()
                .id(entity.getId())
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
                .password(this.password)
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
