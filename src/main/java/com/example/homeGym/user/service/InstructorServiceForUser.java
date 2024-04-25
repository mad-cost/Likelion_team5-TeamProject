package com.example.homeGym.user.service;

import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorServiceForUser {
    private final InstructorRepository instructorRepository;

    public Instructor findById(Long instructorId){
        return instructorRepository.findById(instructorId).orElseThrow();
    }
}
