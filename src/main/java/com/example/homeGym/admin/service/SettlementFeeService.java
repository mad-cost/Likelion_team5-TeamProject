package com.example.homeGym.admin.service;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.admin.dto.SettlementFeeDto;
import com.example.homeGym.admin.entity.SettlementFee;
import com.example.homeGym.admin.repository.SettlementFeeRepository;
import com.example.homeGym.admin.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettlementFeeService {
  private final SettlementFeeRepository settlementFeeRepository;

//  강사의 SettlementFee 가져오기
  public SettlementFeeDto findByInstructorId(Long instructorId) {
    SettlementFeeDto settlementFeeDto = new SettlementFeeDto();
    SettlementFee settlementFee = settlementFeeRepository.findByInstructorId(instructorId);
    settlementFeeDto = SettlementFeeDto.fromEntity(settlementFee);
    return settlementFeeDto;
  }

}
