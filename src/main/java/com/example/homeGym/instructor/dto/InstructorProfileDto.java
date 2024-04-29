package com.example.homeGym.instructor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorProfileDto {
    private List<String> profileImage;
    private String name;
    private Double rating;
}
