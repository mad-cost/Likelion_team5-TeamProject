package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Schedule;
import com.example.homeGym.instructor.entity.Time;
import com.example.homeGym.instructor.entity.Week;
import com.example.homeGym.instructor.repository.ScheduleRepository;
import com.example.homeGym.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AuthenticationFacade facade;

    @Transactional
    public List<ScheduleDto> findAllByOrderByWeek() {
        Instructor currentInstructor = facade.getCurrentInstructor();
        List<ScheduleDto> scheduleDtos = scheduleRepository.findWeekAndTimeAndCreateAtOrderByInstructorId(currentInstructor.getId())
                .stream()
                .sorted(Comparator.comparing(schedule -> {
                    Week weekEnum = Week.valueOf(schedule.getWeek());
                    return weekEnum.getOrder();
                }))
                .map(ScheduleDto::fromEntity)
                .collect(Collectors.toList());
        return scheduleDtos;
    }

    @Transactional
    public List<ScheduleDto> findAllByOrderByTime() {
        Instructor currentInstructor = facade.getCurrentInstructor();
        List<ScheduleDto> scheduleDtos = scheduleRepository.findWeekAndTimeAndCreateAtOrderByInstructorId(currentInstructor.getId())
                .stream()
                .sorted(Comparator.comparing(schedule -> {
                    Time scheduleTime = Time.valueOf(schedule.getTime());
                    return scheduleTime.getStartHour(); // 시작 시간을 기준으로 정렬
                }))
                .map(ScheduleDto::fromEntity)
                .collect(Collectors.toList());
        return scheduleDtos;
    }



    @Transactional
    public ScheduleDto createSchedule(String week, String time) {
        Instructor currentInstructor = facade.getCurrentInstructor();

        Schedule schedule = Schedule.builder()
                .week(week)
                .time(time)
                .instructorName(currentInstructor.getName())
                .instructorId(currentInstructor.getId())
                .build();

        log.info("Creating new schedule for instructor: {}", currentInstructor.getId());

        return ScheduleDto.fromEntity(scheduleRepository.save(schedule));
    }

    @Transactional
    public ScheduleDto updateSchedule(Long scheduleId, String week, String time) {
        Schedule schedule = findScheduleById(scheduleId);

        schedule.setWeek(week);
        schedule.setTime(time);

        log.info("Updating schedule: {}", scheduleId);

        return ScheduleDto.fromEntity(scheduleRepository.save(schedule));
    }

    @Transactional
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = findScheduleById(scheduleId);
        scheduleRepository.delete(schedule);

        log.info("Deleted schedule: {}", scheduleId);
    }

    private Schedule findScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> {
                    log.warn("Schedule not found: {}", scheduleId);
                    return new IllegalArgumentException("Schedule not found");
                });
    }
}
