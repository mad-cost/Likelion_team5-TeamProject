package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.InstructorRepository;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import com.example.homeGym.user.dto.ApplyDto;
import com.example.homeGym.user.entity.Apply;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.ApplyRepository;
import com.example.homeGym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcceptService {
    private final UserProgramRepository userProgramRepository;
    private final UserRepository userRepository;
    private final ApplyRepository applyRepository;
    private final ProgramRepository programRepository;
    private final InstructorRepository instructorRepository;

    //신청한 유저 목록 조회
    public List<ApplyDto> readAllApplyUser(Long instructorId){
        //강사 아이디로 프로그램 목록 가져오기
        List<Program> programList = programRepository.findAllByInstructorId(instructorId);
        List<ApplyDto> applyDtos = new ArrayList<>();
        // 각 프로그램에 대한 신청자 목록 조회
        for (Program program : programList) {
            Long programId = program.getId();
            for (Apply apply: applyRepository.findAllByProgramIdAndApplyState(programId, Apply.ApplyState.PAID)){
                Optional<User> optionalUser = userRepository.findById(apply.getUserId());
                User user = optionalUser.get();
                applyDtos.add(ApplyDto.fromEntity(apply, user.getName(), user.getEmail(), program.getTitle()));
            }
        }
        return applyDtos;
    }
    //수락
    public void accept(Long applyId) {
        Apply apply = applyRepository.findById(applyId).orElseThrow();
        apply.setApplyState(Apply.ApplyState.APPROVED);
        Optional<Program> optionalProgram = programRepository.findById(apply.getProgramId());
        if(optionalProgram.isEmpty()){
            throw new RuntimeException("프로그램이 존재하지 않습니다.");
        }
        Program program = optionalProgram.get();

        // UserProgram 생성
        UserProgram userProgram = new UserProgram();
        userProgram.setState(UserProgram.UserProgramState.IN_PROGRESS);
        userProgram.setUserId(apply.getUserId());
        userProgram.setProgramId(apply.getProgramId());
        userProgram.setCount(0);
        userProgram.setMaxCount(apply.getCount());

        // 회차권에 따른 가격 저장
        if (apply.getCount() == 1) {
            userProgram.setAmount(program.getPrice1());
        } else if (apply.getCount() == 10) {
            userProgram.setAmount(program.getPrice10());
        } else if (apply.getCount() == 20) {
            userProgram.setAmount(program.getPrice20());
        }

        userProgramRepository.save(userProgram);
        applyRepository.save(apply);
    }

    //거절
    public void deny(Long applyId) {
        applyRepository.deleteById(applyId);
    }
}
