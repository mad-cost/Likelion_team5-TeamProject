package com.example.homeGym.user.controller;

import com.example.homeGym.user.dto.ZzimDto;
import com.example.homeGym.user.service.ZzimService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ZzimController {
    private final ZzimService zzimService;

    @GetMapping("{userId}/zzim")
    @ResponseBody
    public String zzimPage(
            @PathVariable("userId")
            Long userId,
            Model model
    ){
        //유저의 찜 목록 가져오기
        List<ZzimDto> zzimDtoList = zzimService.findByUserId(userId);

        System.out.println("zzimDtoList = " + zzimDtoList);
        //프로그램 정보 가져오기


        return "";
    }
}
