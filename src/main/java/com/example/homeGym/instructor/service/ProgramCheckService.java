package com.example.homeGym.instructor.service;

import com.example.homeGym.admin.entity.SettlementFee;
import com.example.homeGym.admin.repository.SettlementFeeRepository;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.ProgramCheckDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.ProgramCheck;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.ProgramCheckRepository;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgramCheckService {
    private final ProgramRepository programRepository;
    private final ProgramCheckRepository programCheckRepository;
    private final UserProgramRepository userProgramRepository;
    private final AuthenticationFacade facade;
    private final SettlementFeeRepository settlementFeeRepository;


    //강사 일지 작성
    @Transactional
    public void createProgramCheck(ProgramCheckDto programCheckDto) {
        ProgramCheck programCheck = programCheckRepository.save(programCheckDto.toEntity());
        log.info("create daily: {}", programCheckDto.getMemo());
        Long userProgramId = programCheckDto.getUserProgramId();
        updateUserProgramCount(userProgramId);
    }

    //일지 작성시 유저 회차권 소모
    @Transactional
    public void updateUserProgramCount(Long userProgramId){
        Optional<UserProgram> optionalUserProgram = userProgramRepository.findById(userProgramId);
        if(optionalUserProgram.isEmpty()){
         throw  new RuntimeException("UserProgram 정보가 없습니다.");
        }
        UserProgram userProgram = optionalUserProgram.get();
        userProgram.setCount(userProgram.getCount()+1);

        // 회차 종료 시 DB에 정보 저장
        if(userProgram.getCount().equals(userProgram.getMaxCount())){
            userProgram.setEndTime(LocalDateTime.now());
            userProgram.setState(UserProgram.UserProgramState.FINISH);
            // instructor 의 정산금에 amount 더해주기
            Instructor instructor = facade.getCurrentInstructor();
            SettlementFee settlementFee = settlementFeeRepository.findByInstructorId(instructor.getId());
            settlementFee.setTotalFee(settlementFee.getTotalFee()+userProgram.getAmount());
            settlementFee.setCurrentFee(settlementFee.getCurrentFee()+userProgram.getAmount());
            settlementFeeRepository.save(settlementFee);
        }
        userProgramRepository.save(userProgram);
    }

    //강사 일지 수정
    public ProgramCheckDto updateProgramCheck(Long id, ProgramCheckDto programCheckDto) {
        ProgramCheck existingProgramCheck = programCheckRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("ProgramCheck with id " + id + " not found"));
        existingProgramCheck.setMemo(programCheckDto.getMemo());
        existingProgramCheck.setProgramDate(programCheckDto.getProgramDate());
        existingProgramCheck.setTime(programCheckDto.getTime());
        programCheckRepository.save(existingProgramCheck);
        return ProgramCheckDto.fromEntity(existingProgramCheck);
    }


    //userProgramId로 모든 programcheck 가져오기
    public List<ProgramCheckDto> getAllProgramChecksByUserProgramId(Long userProgramId) {
        return programCheckRepository.findByUserProgramId(userProgramId).stream()
                .map(ProgramCheckDto::fromEntity)
                .collect(Collectors.toList());
    }

}
