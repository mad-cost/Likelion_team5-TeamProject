package com.example.homeGym.user.controller;

import com.example.homeGym.common.util.AuthenticationUtilService;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.dto.ZzimDto;
import com.example.homeGym.user.service.ProgramServiceForUser;
import com.example.homeGym.user.service.ZzimService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class ZzimController {
    private final ZzimService zzimService;
    private final ProgramServiceForUser programServiceForUser;
    private final AuthenticationUtilService authenticationUtilService;

    @GetMapping("/zzim")
    public String zzimPage(
            Model model,
            Authentication authentication
    ){
        Long userId = authenticationUtilService.getId(authentication);
        //유저의 찜 목록 가져오기
        List<ZzimDto> zzimDtoList = zzimService.findZziom(userId);

        model.addAttribute("zzims", zzimDtoList);

        return "user/zzim";
    }

    @DeleteMapping("/zzim")
    public String deleteZzim(
            @RequestParam("zzimId")
            Long zzimId
    ){
        zzimService.deleteZzim(zzimId);
        return "redirect:zzim";
    }
}
