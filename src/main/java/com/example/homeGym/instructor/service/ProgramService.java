package com.example.homeGym.instructor.service;


import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
  private final ProgramRepository programRepository;
  private final UserProgramRepository userProgramRepository;

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

}
