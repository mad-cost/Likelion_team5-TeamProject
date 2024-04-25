package com.example.homeGym.instructor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProgramDetailDto {
    private String userName;
    private Long userId;
    private Integer count;
    private Integer maxCount;
}
