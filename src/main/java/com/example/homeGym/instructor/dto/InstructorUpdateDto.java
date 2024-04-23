package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Gender;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstructorUpdateDto {
    private Long id;
    private String loginId;
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

    public void updateEntity(Instructor instructor) {
        instructor.setName(this.getName());
        instructor.setEmail(this.getEmail());
        instructor.setGender(this.getGender());
        instructor.setBirthyear(this.getBirthyear());
        instructor.setBirthday(this.getBirthday());
        instructor.setPhone(this.getPhone());
        instructor.setCareer(this.getCareer());
        instructor.setProfileImageUrl(this.getProfileImageUrl());
        instructor.setCertificate(this.getCertificate());
        instructor.setBank(this.getBank());
        instructor.setBankName(this.getBankName());
        instructor.setAccount(this.getAccount());
    }
}
