package com.example.homeGym.mail;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    private String code;
}
