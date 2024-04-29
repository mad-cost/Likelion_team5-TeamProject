package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.InstructorAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorAddressRepository extends JpaRepository<InstructorAddress, Long> {
    List<InstructorAddress> findByInstructorId(Long instructorId);

}
