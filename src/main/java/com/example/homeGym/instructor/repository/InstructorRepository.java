package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    boolean existsByLoginId(String loginId);
    boolean existsByEmail(String email);

    List<Instructor> findAllByOrderByName();
}
