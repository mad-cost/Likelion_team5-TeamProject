package com.example.homeGym.admin.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettlementFeeDto {
  private Long id;
  private Integer currentFee;
  private Integer totalFee;
  private Long instructorId;
  private LocalDateTime createdAt;
}
