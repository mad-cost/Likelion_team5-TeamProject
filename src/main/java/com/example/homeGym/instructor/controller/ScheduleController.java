package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("instructor/{instructorId}/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    // 강사 스케쥴
    @GetMapping()
    public String readSchedule(
            @PathVariable("instructorId") Long instructorId,
            Model model
    ) {
        ScheduleDto scheduleDto = scheduleService.readSchedule(instructorId);
        model.addAttribute("scheduleDto", scheduleDto);
        return "schedule";
    }

    // 스케줄 생성
    @PostMapping()
    public String createSchedule(
            @RequestPart("week") String week,
            @RequestPart("time") String time,
            Model model
    ) {
        ScheduleDto scheduleDto = scheduleService.createSchedule(week, time);
        model.addAttribute("scheduleDto", scheduleDto);
        return "redirect:/instructor/{instructorId}/schedule";
    }

    // 스켸줄 수정
    @PutMapping("/{scheduleId}")
    public String updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestPart("week") String week,
            @RequestPart("time") String time,
            Model model
    ) {
        ScheduleDto scheduleDto = scheduleService.updateSchedule(scheduleId, week, time);
        model.addAttribute("scheduleDto", scheduleDto);
        return "redirect:/instructor/{instructorId}/schedule";
    }

    // 스케줄 삭제
    @DeleteMapping("{scheduleId}")
    public String deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId
    ) {
        scheduleService.deleteSchedule(scheduleId);
        return "redirect:/instructor/{instructorId}/schedule";
    }
}
