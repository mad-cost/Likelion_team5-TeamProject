package com.example.homeGym.auth.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {

    private String email;
    private String password;
}
