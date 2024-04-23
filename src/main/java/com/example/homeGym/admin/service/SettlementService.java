package com.example.homeGym.admin.service;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.admin.dto.SettlementFeeDto;
import com.example.homeGym.admin.entity.SettlementFee;
import com.example.homeGym.admin.repository.SettlementFeeRepository;
import com.example.homeGym.admin.repository.SettlementRepository;
import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementFeeRepository settlementFeeRepository;
    private final SettlementRepository settlementRepository;

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


}
