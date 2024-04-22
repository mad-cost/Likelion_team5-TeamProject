package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.InstructorCreateDto;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorRepository instructorRepository;

    //강사 회원 가입
    //REGISTRATION_PENDING 상태로 DB에 저장
    public void createInstructor(InstructorCreateDto dto){
        log.info("Creating instructor with name: {}", dto.getName());
        instructorRepository.save(dto.toEntity());
    }
    //로그인 아이디 존재 확인
    public boolean isLoginIdAvailable(String loginId) {
        return !instructorRepository.existsByLoginId(loginId);
    }

    //회원탈퇴 신청
    public String withdrawalProposal(Long instructorId) {
        Optional<Instructor> instructorOpt = instructorRepository.findById(instructorId);

        if (!instructorOpt.isPresent()) {
            return "강사 정보를 찾을 수 없습니다.";
        }

        Instructor instructor = instructorOpt.get();
        if (instructor.getState() == Instructor.InstructorState.WITHDRAWAL_PENDING) {
            return "이미 탈퇴 대기 상태인 회원입니다.";
        }

        instructor.setState(Instructor.InstructorState.WITHDRAWAL_PENDING);
        instructorRepository.save(instructor);
        return "탈퇴 신청이 완료되었습니다.";
    }

    // 강사 이름 순서로 나열
    public List<InstructorDto> findAllByOrderByName() {
        List<InstructorDto> instructorDtos = new ArrayList<>();
        for (Instructor instructor : instructorRepository.findAllByOrderByName()) {
            instructorDtos.add(InstructorDto.fromEntity(instructor));
        }
        return instructorDtos;
    }

    public InstructorDto findById(Long instructorId){
        return InstructorDto.fromEntity(instructorRepository.findById(instructorId).orElseThrow());
    }

}
