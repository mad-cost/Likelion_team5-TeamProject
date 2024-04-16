package com.example.homeGym.auth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtRequestDto {
    private String userId;
    private String password;

}
