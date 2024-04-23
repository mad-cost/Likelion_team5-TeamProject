package com.example.homeGym.admin.controller;

import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.service.InstructorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/settlement")
public class AdSettlementController {
  private final InstructorService instructorService;

  @GetMapping
  public String settlement(
          Model model
  ){
    model.addAttribute("instructors", instructorService.findAllByOrderByName());

    return "/admin/settlement";
  }



}
