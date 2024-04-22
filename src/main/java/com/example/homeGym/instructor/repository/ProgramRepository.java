package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

//    Program save(Program program, Instructor instructor);
}
