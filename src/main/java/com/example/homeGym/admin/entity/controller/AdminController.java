package com.example.homeGym.admin.entity.controller;

import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
  private final UserService userService;


  @GetMapping
  public String admin(
          Model model
  ){
    model.addAttribute("users", userService.findAllByOrderByName());
    return "admin";
  }


}
