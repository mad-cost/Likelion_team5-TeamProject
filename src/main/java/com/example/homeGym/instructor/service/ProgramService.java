package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProgramService {
  private final ProgramRepository programRepository;

  public List<ProgramDto> findByProgramId(List<Long> id){
    List<ProgramDto> programDtos = new ArrayList<>();
    for (Program program : programRepository.findAllById(id)){
      programDtos.add(ProgramDto.fromEntity(program));
    }
    return programDtos;
  }
}
