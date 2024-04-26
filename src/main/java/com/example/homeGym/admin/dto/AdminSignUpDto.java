package com.example.homeGym.admin.dto;

import com.example.homeGym.instructor.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminSignUpDto {

    private String name;
    private String email;
    private String password;
    private Gender gender;
    private String birthyear;
    private String birthday;
    private String state;
    private String profileImageUrl;
    private String phone;
}
