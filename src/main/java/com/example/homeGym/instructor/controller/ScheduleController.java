package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.service.InstructorAddressService;
import com.example.homeGym.instructor.service.ScheduleService;
import com.example.homeGym.common.util.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/instructor/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final AuthenticationFacade facade;
    private final InstructorAddressService instructorAddressService;

    private boolean isAuthenticated(Long instructorId) {
        Long currentInstructorId = facade.getCurrentInstructor().getId();
        return currentInstructorId.equals(instructorId);
    }

    @GetMapping()
    public String readSchedule(
            @RequestParam(value = "orderBy", defaultValue = "week") String orderBy,
            Model model
    ) {
        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!isAuthenticated(currentInstructor.getId())) {
            throw new IllegalArgumentException("Authentication failed");
        }

        // 현재 강사의 이름을 모델에 추가
        model.addAttribute("instructorName", currentInstructor.getName());

        List<ScheduleDto> scheduleDtos;
        if ("time".equals(orderBy)) {
            scheduleDtos = scheduleService.findAllByOrderByTime();
        } else { // Default to orderBy week
            scheduleDtos = scheduleService.findAllByOrderByWeek();
        }

        model.addAttribute("scheduleDtos", scheduleDtos);
        model.addAttribute("orderBy", orderBy); // Add orderBy to model for template

        model.addAttribute("instructorAddresses", instructorAddressService.getInstructorAddresses(currentInstructor.getId()));


        return "instructor/schedule/instructor-schedule";
    }
    @PostMapping("/address")
    public String addInstructorAddress(@RequestParam String siDo, @RequestParam String siGunGu, @RequestParam String dong) {
        Instructor instructor = facade.getCurrentInstructor();
        instructorAddressService.saveInstructorAddress(siDo, siGunGu, dong, instructor.getId());
        return "redirect:/instructor/schedule"; // 주소 등록 페이지로 리다이렉트
    }

    @DeleteMapping("/address/{addressId}")
    public String deleteInstructorAddress(@PathVariable("addressId") Long addressId) {
        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!isAuthenticated(currentInstructor.getId())) {
            throw new IllegalArgumentException("Authentication failed");
        }

        instructorAddressService.deleteInstructorAddress(addressId);
        return "redirect:/instructor/schedule";
    }


    @PostMapping()
    public String createSchedule(
            @RequestParam("week") String week,
            @RequestParam("time") String time,
            Model model
    ) {
        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!isAuthenticated(currentInstructor.getId())) {
            throw new IllegalArgumentException("Authentication failed");
        }

        ScheduleDto scheduleDto = scheduleService.createSchedule(week, time);
        model.addAttribute("scheduleDto", scheduleDto);
        return "redirect:/instructor/schedule";
    }

    @PutMapping("/{scheduleId}")
    public String updateSchedule(
            @PathVariable("scheduleId") Long scheduleId,
            @RequestParam("week") String week,
            @RequestParam("time") String time,
            Model model
    ) {
        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!isAuthenticated(currentInstructor.getId())) {
            throw new IllegalArgumentException("Authentication failed");
        }

        ScheduleDto scheduleDto = scheduleService.updateSchedule(scheduleId, week, time);
        model.addAttribute("scheduleDto", scheduleDto);
        return "redirect:/instructor/schedule";
    }

    @DeleteMapping("/{scheduleId}")
    public String deleteSchedule(
            @PathVariable("scheduleId") Long scheduleId
    ) {
        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!isAuthenticated(currentInstructor.getId())) {
            throw new IllegalArgumentException("Authentication failed");
        }

        scheduleService.deleteSchedule(scheduleId);
        return "redirect:/instructor/schedule";
    }
}
