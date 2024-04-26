package com.example.homeGym.admin.service;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.admin.entity.Settlement;
import com.example.homeGym.admin.repository.SettlementRepository;
import com.example.homeGym.admin.dto.SettlementFeeDto;
import com.example.homeGym.admin.entity.SettlementFee;
import com.example.homeGym.admin.repository.SettlementFeeRepository;
import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementFeeRepository settlementFeeRepository;
    private final SettlementRepository settlementRepository;

  public List<Settlement> findAll(){
    return settlementRepository.findAll();
  }
    //정산 신청
    @Transactional
    public void settlementApply(SettlementDto dto){

        log.info("정산 신청 시작: {}", dto);  // 시작 로그

        //강사 id로 현재 정산 가능 금액 가져오기
        SettlementFee settlementFee = settlementFeeRepository.findByInstructorId(dto.getInstructorId());
        if (settlementFee == null) {
            log.error("정산 정보 없음: 강사 ID {}", dto.getInstructorId());
            throw new GlobalExceptionHandler(
                    CustomGlobalErrorCode.SETTLEMENT_NOT_FOUND
            );
        }

        Integer currentFee = settlementFee.getCurrentFee();
        Integer totalFee = settlementFee.getTotalFee();
        //정산 받고 싶은 금액 가져오기
        Integer amount = dto.getAmount();
        // 정산 받고 싶은 금액이 0 이하 -> 에러
        if (amount <= 0) {
            log.error("정산 금액 유효하지 않음: 요청 금액 {}", amount);
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SETTLEMENT_INVALID);
        }

        //정산 받고 싶은 금액 > 정산 가능 금액 -> 에러
        if (amount > currentFee) {
            log.error("정산 가능 금액 초과: 요청 금액 {}, 가능 금액 {}", amount, currentFee);
            throw new GlobalExceptionHandler(
                    CustomGlobalErrorCode.SETTLEMENT_EXCEEDS_AVAILABLE
            );
        }
        //정산서 작성
        settlementRepository.save(dto.toEntity());
        //강사 정산금 조정
        settlementFee.setCurrentFee(currentFee-amount);
        settlementFee.setTotalFee(totalFee+amount);
        settlementFeeRepository.save(settlementFee);
        log.info("정산 신청 완료: 강사 ID {}, 정산 금액 {}", dto.getInstructorId(), amount);
    }
    //정산금 가져오기
    public SettlementFeeDto settlementFeeFindByInstructorId(Long instructorId) {
        SettlementFee settlementFee = settlementFeeRepository.findByInstructorId(instructorId);
        if (settlementFee == null) {
            // 적절한 예외 처리, 정산 정보를 찾을 수 없을 때
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.SETTLEMENT_NOT_FOUND);
        }
        return SettlementFeeDto.fromEntity(settlementFee);
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
