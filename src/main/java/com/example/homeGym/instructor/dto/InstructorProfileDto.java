package com.example.homeGym.instructor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorProfileDto {
    private String profileImage;
    private String name;
    private Double rating;
}
