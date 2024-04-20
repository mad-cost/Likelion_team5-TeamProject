package com.example.homeGym.admin.controller;


import com.example.homeGym.admin.service.NumberUtils;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.instructor.service.UserProgramService;
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
@RequestMapping("/admin/instructor")
public class AdminInstController {
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
    model.addAttribute("instructor", instructorService.findById(instructorId)); // 1
//    강사의 모든 프로그램 가져오기
    List<ProgramDto> programDtos= programService.findAllByInstructorIdConvertId(instructorId); // 1, 4
//    강사의 모픈 프로그램 형변환 Dto -> Long
    List<Long> programLongId = programService.ConvertLong(programDtos); // 1, 4
//    programId에 해당하는 user_program의 id값들의 Total금액 가져오기, Program에 값 저장
    List<Integer> totalAmount = userProgramService.totalAmount(programLongId); // 300000, 30000
    log.info("1번 프로그램의 Total 금액 : {}",totalAmount.get(0)); //300000
    log.info("4번 프로그램의 Total 금액 : {}",totalAmount.get(1)); //150000
//    이번달 금액 가져오기
    List<Integer> monthAmount = userProgramService.monthAmount(programLongId);
    log.info("1번 프로그램의 이번달 금액 : {}",monthAmount.get(0)); // 190000
    log.info("4번 프로그램의 이번달 금액 : {}",monthAmount.get(1)); // 110000
//    각각의 Program id값에 해당하는 totalAmount, monthAmount 넣어주기
    for (int i = 0; i < programDtos.size(); i++) {
      ProgramDto programDto = programDtos.get(i);
      programDto.setTotalAmount(numberUtils.addCommasToNumber(totalAmount.get(i)));
      programDto.setMonthAmount(numberUtils.addCommasToNumber(monthAmount.get(i)));
    }
    log.info("@@@zeroToTotal{}",programDtos.get(0).getTotalAmount());
    log.info("@@@zeroToTotal{}",programDtos.get(1).getTotalAmount());
    log.info("@@@zeroToMonth{}",programDtos.get(0).getMonthAmount());
    log.info("@@@zeroToMonth{}",programDtos.get(1).getMonthAmount());

    model.addAttribute("programs", programDtos);
    return "/admin/instructor";
  }
}

