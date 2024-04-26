package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.ProgramCheckDto;
import com.example.homeGym.instructor.entity.ProgramCheck;
import com.example.homeGym.instructor.repository.ProgramCheckRepository;
import com.example.homeGym.instructor.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProgramCheckService {
    private final ProgramRepository programRepository;
    private final ProgramCheckRepository programCheckRepository;


    //강사 일지 작성
    public ProgramCheckDto createProgramCheck(ProgramCheckDto programCheckDto) {
        ProgramCheck programCheck = programCheckRepository.save(programCheckDto.toEntity());
        log.info("create daily: {}", programCheckDto.getMemo());
        return ProgramCheckDto.fromEntity(programCheck);
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


    public ProgramCheckDto getProgramCheckById(Long id) {
        ProgramCheck programCheck = programCheckRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("ProgramCheck with id " + id + " not found"));
        return ProgramCheckDto.fromEntity(programCheck);
    }

    //userProgramId로 모든 programcheck 가져오기
    public List<ProgramCheckDto> getAllProgramChecksByUserProgramId(Long userProgramId) {
        return programCheckRepository.findByUserProgramId(userProgramId).stream()
                .map(ProgramCheckDto::fromEntity)
                .collect(Collectors.toList());
    }

    //삭제하기
    public void deleteProgramCheck(Long id) {
        programCheckRepository.deleteById(id);
    }

}
