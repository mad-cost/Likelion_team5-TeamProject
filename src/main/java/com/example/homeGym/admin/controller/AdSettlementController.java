package com.example.homeGym.admin.controller;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.admin.entity.Settlement;
import com.example.homeGym.admin.repository.SettlementRepository;
import com.example.homeGym.admin.service.NumberUtils;
import com.example.homeGym.admin.service.SettlementService;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/settlement")
public class AdSettlementController {
  private final SettlementService settlementService;
  private final InstructorService instructorService;
  private final NumberUtils numberUtils;


  @GetMapping()
  public String settlement(
          Model model
  ){
//    Settlementd의 state가 SETTLEMENT_PENDING인 값들 모두 가져오기 + date : yyyy-mm-dd
    List<SettlementDto> settlementsPending = settlementService.findAllByStateIsPENDING();
//    금액에 , 찍어주기
    for (SettlementDto settlementPending : settlementsPending) {
      settlementPending.setNewAmount(numberUtils.addCommasToNumber(settlementPending.getAmount()));
    }

//  Instruction객체 가져오기
    for (SettlementDto dto : settlementsPending){
      Long instructorId = dto.getInstructorId();
      dto.setInstructor(instructorService.findByLongId(instructorId));
    }
    model.addAttribute("settlementsPending", settlementsPending);

//    Settlementd의 state가 COMPLETE 값들 모두 가져오기 + date : yyyy-mm-dd
    List<SettlementDto> settlementsComplete = settlementService.findAllByStateIsComplete();
//    금액에 , 찍어주기
    for (SettlementDto settlementPending : settlementsComplete) {
      settlementPending.setNewAmount(numberUtils.addCommasToNumber(settlementPending.getAmount()));
    }
    //  Instruction객체 가져오기
    for (SettlementDto dto : settlementsComplete){
      Long instructorId = dto.getInstructorId();
      dto.setInstructor(instructorService.findByLongId(instructorId));
    }
    model.addAttribute("settlementsComplete", settlementsComplete);


    return "/admin/settlement";
  }

  @GetMapping("/{pendingId}")
  public String pendingId(
          @PathVariable("pendingId")
          Long pendingId
  ){
    settlementService.updateState(pendingId);
    return "redirect:/admin/settlement";
  }

}
