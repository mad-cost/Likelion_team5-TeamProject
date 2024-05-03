package com.example.homeGym.admin.dto;

import com.example.homeGym.admin.entity.SettlementFee;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
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

  public static SettlementFeeDto fromEntity(SettlementFee entity){
    SettlementFeeDto.SettlementFeeDtoBuilder builder = SettlementFeeDto.builder()
            .id(entity.getId())
            .currentFee(entity.getCurrentFee())
            .totalFee(entity.getTotalFee())
            .instructorId(entity.getInstructorId())
            .createdAt(entity.getCreatedAt());
    return builder.build();
  }

}
