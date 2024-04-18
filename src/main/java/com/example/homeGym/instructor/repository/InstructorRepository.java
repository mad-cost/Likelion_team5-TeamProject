package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean existsByLoginId(String loginId);
}
