package com.example.homeGym.instructor.service;

import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Schedule;
import com.example.homeGym.instructor.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final AuthenticationFacade facade;

    // 스케줄 보기
    public ScheduleDto readSchedule(Long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        // schedule 존재 여부
        if (optionalSchedule.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SCHEDULE_NOT_EXISTS);

        Schedule schedule = optionalSchedule.get();
        return ScheduleDto.fromEntity(schedule);
    }


    // 스케줄 생성
    public ScheduleDto createSchedule(
            String week,
            String time
    ) {
        Instructor currentInstructor = facade.getCurrentInstructor();

        Schedule schedule= Schedule.builder()
                .week(week)
                .time(time)
//                .instructor(currentInstructor)
                .build();


        return ScheduleDto.fromEntity(scheduleRepository.save(schedule));
    }

    // 스케쥴 수정
    public ScheduleDto updateSchedule(
            Long scheduleId,
            String week,
            String time
    ) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
//         존재하지 않는 Schedule일 경우
        if (optionalSchedule.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SCHEDULE_NOT_EXISTS);

        Schedule schedule = optionalSchedule.get();

        // 스케줄 수정
        schedule.setWeek(week);
        schedule.setTime(time);

        return ScheduleDto.fromEntity(scheduleRepository.save(schedule));
    }

    // 스케줄 삭제
    public void deleteSchedule(Long scheduleId) {
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(scheduleId);
        // schedule이 존재하지 않는 경우
        if (optionalSchedule.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SCHEDULE_NOT_EXISTS);

        Schedule schedule = optionalSchedule.get();

        scheduleRepository.delete(schedule);
    }
}
