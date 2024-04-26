package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.ProgramCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.List;

import java.util.List;


public interface ProgramCheckRepository extends JpaRepository<ProgramCheck, Long> {
    List<ProgramCheck> findByUserProgramId(Long userProgramId);
}
