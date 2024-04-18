package com.example.homeGym.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/")
    public String mainPage(){
        return "main";
    }


}
