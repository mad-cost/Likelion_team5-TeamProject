package com.example.homeGym.user.controller;

import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.dto.ZzimDto;
import com.example.homeGym.user.service.ProgramServiceForUser;
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
    private final ProgramServiceForUser programServiceForUser;

    @GetMapping("/zzim")
    public String zzimPage(
            Model model
    ){
        //유저의 찜 목록 가져오기
        List<ZzimDto> zzimDtoList = zzimService.findByUserId(1L);

        System.out.println("zzimDtoList = " + zzimDtoList);
        //프로그램 정보 가져오기
        for (ZzimDto dto :
                zzimDtoList) {
            //TODO findById 가 없을때, 에러남 .. 시나리오상 그럴일은 없지만 예외처리 방법 생각할것.
            dto.setProgram(programServiceForUser.findById(dto.getProgramId()));
        }

        model.addAttribute("zzims", zzimDtoList);

        return "user/zzim";
    }
}
