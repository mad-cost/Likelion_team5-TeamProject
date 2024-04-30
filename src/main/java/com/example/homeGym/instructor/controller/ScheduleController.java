package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Schedule;
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

    //주소 검색 보여주는 요청과 스케줄 보내주는 요청 분리
    @GetMapping()
    public String readAddress(
            Model model
    ) {
        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!isAuthenticated(currentInstructor.getId())) {
            throw new IllegalArgumentException("Authentication failed");
        }

        // 현재 강사의 이름을 모델에 추가
        model.addAttribute("instructorId", currentInstructor.getId());
        model.addAttribute("instructorName", currentInstructor.getName());
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

    @GetMapping("/all")
    @ResponseBody
    public List<ScheduleDto> getAllSchedules() {
        Instructor instructor = facade.getCurrentInstructor();
        return scheduleService.getAllSchedules(instructor.getId());
    }

    @PostMapping("/save")
    @ResponseBody
    public void saveSchedule(@RequestBody List<Schedule> schedules) {
       Instructor instructor = facade.getCurrentInstructor();
        scheduleService.saveSchedules(schedules, instructor.getId(), instructor.getName());
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public void deleteCanceledSchedules(@RequestBody List<ScheduleDto> canceledSchedules) {
        scheduleService.deleteCanceledSchedules(canceledSchedules);
    }

}
