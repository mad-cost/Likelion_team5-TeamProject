package com.example.homeGym.admin.service;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.admin.entity.Settlement;
import com.example.homeGym.admin.repository.SettlementRepository;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SettlementService {
  private final SettlementRepository settlementRepository;

  public List<Settlement> findAll(){
    return settlementRepository.findAll();
  }

//  Settlement에서 state가 SETTLEMENT_PENDING 찾기
  public List<SettlementDto> findAllByStateIsPENDING(){
    List<SettlementDto> settlementDto = new ArrayList<>();
//    모든 Settlement 가져오기
    List<Settlement> settlements = settlementRepository.findAll();

    for (Settlement settlement : settlements){
      if (settlement.getState().equals(Settlement.SettlementState.SETTLEMENT_PENDING)){
        //        날짜를 yyyy-mm-dd 모습으로 바꿔주기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        log.info("@@@@GetCreatedAt 수정 전 : {} ",settlement.getCreatedAt());
        String changeDate = settlement.getCreatedAt().format(formatter);
        log.info("@@@@GetCreatedAt 수정 후 : {} ",changeDate);
        settlement.setDateCreateAt(changeDate);
        settlementRepository.save(settlement);
        settlementDto.add(SettlementDto.fromEntity(settlement));

      }
    }
    return settlementDto;
  }
  //  Settlement에서 state가 COMPLETE 찾기
  public List<SettlementDto> findAllByStateIsComplete(){
    List<SettlementDto> settlementDto = new ArrayList<>();
    List<Settlement> settlements = settlementRepository.findAll();
    for (Settlement settlement : settlements){
      if (settlement.getState().equals(Settlement.SettlementState.COMPLETE)){
        //        날짜를 yyyy-mm-dd 모습으로 바꿔주기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateCreateAt = settlement.getCreatedAt().format(formatter);
        settlement.setDateCreateAt(dateCreateAt);

        log.info("####GetCreatedAt 수정 전 : {} ",settlement.getCompleteTime());
        String dateCompleteTime = settlement.getCompleteTime().format(formatter);
        log.info("####GetCreatedAt 수정 후 : {} ",dateCompleteTime);
        settlement.setDateCompleteTime(dateCompleteTime);

        settlementRepository.save(settlement);
        settlementDto.add(SettlementDto.fromEntity(settlement));
      }
    }
    return settlementDto;
  }

  public void updateState(Long pendingId){
    Settlement settlement = settlementRepository.findById(pendingId).orElseThrow();
//    state를 COMPLETE로 바꿔주기
    settlement.setState(Settlement.SettlementState.COMPLETE);
//    LocalDateTime 넣어주기
    settlement.setCompleteTime(LocalDateTime.now());
    settlementRepository.save(settlement);
    SettlementDto.fromEntity(settlement);
  }
}
