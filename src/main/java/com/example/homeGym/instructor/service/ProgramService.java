package com.example.homeGym.instructor.service;


import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.repository.ProgramRepository;
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
  private final AuthenticationFacade facade;


  // 프로그램 목록
  public List<ProgramDto> findByProgramId(List<Long> id){
    List<ProgramDto> programDtos = new ArrayList<>();
    for (Program program : programRepository.findAllById(id)){
      programDtos.add(ProgramDto.fromEntity(program));
    }
    return programDtos;
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


}
