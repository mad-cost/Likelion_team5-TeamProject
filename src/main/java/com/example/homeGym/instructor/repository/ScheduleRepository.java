package com.example.homeGym.instructor.repository;

import com.example.homeGym.instructor.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByInstructorIdOrderByWeekAscTimeAsc(Long instructorId);
}
