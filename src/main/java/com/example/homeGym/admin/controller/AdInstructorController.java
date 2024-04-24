package com.example.homeGym.admin.controller;


import com.example.homeGym.admin.service.NumberUtils;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.instructor.service.UserProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/instructor")
public class AdInstructorController {
  private final InstructorService instructorService;
  private final ProgramService programService;
  private final UserProgramService userProgramService;
  private final NumberUtils numberUtils;

  @GetMapping("/{instructorId}")
  public String instructorId(
          @PathVariable("instructorId")
          Long instructorId,
          Model model
  ) {
    model.addAttribute("instructor", instructorService.findById(instructorId));
//    강사의 모든 프로그램 가져오기
    List<ProgramDto> programDtos= programService.findAllByInstructorIdConvertId(instructorId);
//    강사의 모픈 프로그램 형변환 Dto -> Long
    List<Long> programLongId = programService.ConvertLong(programDtos);
//    programId에 해당하는 user_program의 id값들의 Total금액 가져오기, Program에 값 저장
    List<Integer> totalAmount = userProgramService.totalAmount(programLongId);


    List<Integer> monthAmount = userProgramService.monthAmount(programLongId);
//    각각의 Program id값에 해당하는 totalAmount, monthAmount 넣어주기
    for (int i = 0; i < programDtos.size(); i++) {
      ProgramDto programDto = programDtos.get(i);
      programDto.setTotalAmount(numberUtils.addCommasToNumber(totalAmount.get(i)));
      programDto.setMonthAmount(numberUtils.addCommasToNumber(monthAmount.get(i)));
    }

    model.addAttribute("programs", programDtos);
    return "/admin/instructor";
  }

  @PostMapping("/{instructorId}/medal")
  public String medal(
          @PathVariable("instructorId")
          Long instructorId,
          @RequestParam(value = "Gold", required = false) String Gold,
          @RequestParam(value = "Silver", required = false) String Silver,
          @RequestParam(value = "Bronze", required = false) String Bronze,
          @RequestParam(value = "Unranked", required = false) String Unranked
  ){
    String medal = instructorService.findRank(Gold, Silver, Bronze, Unranked);
    instructorService.saveMedal(instructorId, medal);
    return "redirect:/admin/instructor/{instructorId}";
  }
//  강사 승인 페이지
  @GetMapping("/accept")
  public String accept(
          Model model
  ){
    //강사의 상태가 REGISTRATION인 강사 모두 가져오기
    List<InstructorDto> instructorDto = instructorService.findAllByStateIsREGISTRATION();
    model.addAttribute("instructors", instructorDto);
    return "/admin/accept";
  }
// 강사 승인하기
  @PostMapping("{instructorId}/accept")
  public String instIdAccept(
          @PathVariable("instructorId")
          Long instructorId
  ){
//    강사 승인 state 변경하기
    instructorService.accept(instructorId);
    return "redirect:/admin/instructor/accept";
  }
 //강사 거절하기
  @PostMapping("{instructorId}/delete")
  public String instIdDelete(
          @PathVariable("instructorId")
          Long instructorId
  ){
//    강사 거절
    instructorService.delete(instructorId);
    return "redirect:/admin/instructor/accept";
  }
}

