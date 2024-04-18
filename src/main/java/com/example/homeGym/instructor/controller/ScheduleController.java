package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("instructor/{instructorId}/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 강사 스케쥴
    @GetMapping()
    public ScheduleDto readSchedule(
            @PathVariable("instructorId")
            Long instructorId
    ) {
        return scheduleService.readSchedule(instructorId);
    }

    // 스케줄 생성
    @PostMapping()
    public ScheduleDto createSchedule(
            @RequestPart("week")
            String week,
            @RequestPart("time")
            String time
    ) {
        return scheduleService.createSchedule(week, time);
    }

    // 스켸줄 수정
    @PutMapping("/{scheduleId}")
    public ScheduleDto updateSchedule(
            @PathVariable("scheduleId")
            Long scheduleId,
            @RequestPart("week")
            String week,
            @RequestPart("time")
            String time
    ) {
        return scheduleService.updateSchedule(scheduleId, week, time);
    }

    // 스케줄 삭제
    @DeleteMapping("{scheduleId}")
    public void deleteSchedule(
            @PathVariable("scheduleId")
            Long scheduleId
    ) {
        scheduleService.deleteSchedule(scheduleId);
    }
}
