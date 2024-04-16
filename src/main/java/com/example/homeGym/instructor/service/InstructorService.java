package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.InstructorCreateDto;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    //강사 회원 가입 신청
    public void createInstructor(InstructorCreateDto dto){
        instructorRepository.save(dto.toEntity());
    }

}
