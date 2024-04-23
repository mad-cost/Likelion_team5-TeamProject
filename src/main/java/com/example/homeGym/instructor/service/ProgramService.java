package com.example.homeGym.instructor.service;


import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgramService {
  private final ProgramRepository programRepository;
  private final UserProgramRepository userProgramRepository;
  private final AuthenticationFacade facade;

  // 프로그램 목록
  public List<ProgramDto> findByProgramId(List<Long> id){
    List<ProgramDto> programDtos = new ArrayList<>();
    for (Program program : programRepository.findAllById(id)){
      programDtos.add(ProgramDto.fromEntity(program));
    }
    return programDtos;
  }

  public List<ProgramDto> findAllByInstructorIdConvertId(Long instructorId){
    List<ProgramDto> programs = new ArrayList<>();
    for (Program program : programRepository.findAllByInstructorId(instructorId)){
      programs.add(ProgramDto.fromEntity(program));
    }
    return programs;
  }

  public List<Long> ConvertLong(List<ProgramDto> programs){
    List<Long> programIds = new ArrayList<>();

    for (ProgramDto program : programs){
      programIds.add(program.getId());
    }
    return programIds;
  }

//  public List<ProgramDto> findAllByInstructorIdConvertProgramId(Long instructorId){
//    List<Program> programs = new ArrayList<>();
//    for (Program program : programRepository.findAllByInstructorId(instructorId)){
//      programs.add(ProgramDto.fromEntity(program.getId()));
//    }
//  }

  // 프로그램 생성
  @Transactional
  public ProgramDto createProgram(ProgramDto programDto) {
    Instructor instructor = facade.getCurrentInstructor();

    Program program = Program.builder()
            .id(programDto.getId())
            .category(programDto.getCategory())
            .title(programDto.getTitle())
            .description(programDto.getDescription())
            .supplies(programDto.getSupplies())
            .curriculum(programDto.getCurriculum())
            .price1(programDto.getPrice1())
            .price10(programDto.getPrice1())
            .price20(programDto.getPrice20())
            .build();

    return ProgramDto.fromEntity(programRepository.save(program));
  }

  // 프로그램 수정
  @Transactional
  public void updateProgram(Long programId, ProgramDto programDto) {
    Optional<Program> optionalProgram =programRepository.findById(programId);
    // 프로그램이 없을 경우
    if (optionalProgram.isEmpty())
      throw new GlobalExceptionHandler(CustomGlobalErrorCode.PROGRAM_NOT_EXISTS);

    Program program = optionalProgram.get();
    Instructor instructor = facade.getCurrentInstructor();

    // 프로그램 강사가 현재 접속 강사와 다르면
//    if (!program.get)
  }
//  모든 프로그램 가져오기
  public List<Program> findAll(){
    return programRepository.findAll();
  }
//  state가 CREATION_PENDING 가져오기
  public List<ProgramDto> findAllByStateIsCreation(List<Program> programs){
    List<ProgramDto> stateCreateDto = new ArrayList<>();
    for (Program program : programs){
      if (program.getState() == Program.ProgramState.CREATION_PENDING){
        stateCreateDto.add(ProgramDto.fromEntity(program));
      }
    }
    return stateCreateDto;
  }
//  신규 등록 프로그램 CREATION_PENDING -> IN_PROGRESS
  public void stateConvertInProgress(Long programId){
    Program program = programRepository.findById(programId).orElseThrow();

    program.setState(Program.ProgramState.IN_PROGRESS);
    programRepository.save(program);
  }
//  신규 등록 프로그램 거절
  public void deleteInProgress(Long programId){
    Program program = programRepository.findById(programId).orElseThrow();
    programRepository.delete(program);
  }



}
