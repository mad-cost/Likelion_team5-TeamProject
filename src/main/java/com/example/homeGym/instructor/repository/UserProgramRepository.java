package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.UserProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProgramRepository extends JpaRepository<UserProgram, Long> {
}
