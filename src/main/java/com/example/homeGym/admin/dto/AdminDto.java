package com.example.homeGym.admin.dto;

import com.example.homeGym.instructor.entity.Gender;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    private Long id;
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
