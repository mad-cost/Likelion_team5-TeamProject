package com.example.homeGym.user.repository;

import com.example.homeGym.instructor.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepositoryForUser extends JpaRepository<Program, Long> {
}
