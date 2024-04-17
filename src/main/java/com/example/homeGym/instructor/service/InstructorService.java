package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.InstructorCreateDto;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    //강사 회원 가입
    //REGISTRATION_PENDING 상태로 DB에 저장
    public void createInstructor(InstructorCreateDto dto){
        instructorRepository.save(dto.toEntity());
    }

    //회원탈퇴 신청
    public void withdrawalProposal(Long instructorId) {
        Optional<Instructor> instructor = instructorRepository.findById(instructorId);

        if (instructor.isPresent()) {
            Instructor existingInstructor = instructor.get();
            existingInstructor.setState(Instructor.InstructorState.WITHDRAWAL_PENDING);
            instructorRepository.save(existingInstructor);
        }
    }

}
