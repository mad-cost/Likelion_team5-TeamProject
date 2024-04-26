package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {

  List<Program> findAllByInstructorId(Long id);

  List<Program> findByInstructorId(Long instructorId);
  Optional<Program> findById(Long id);
}
