package com.example.homeGym.user.dto;

import com.example.homeGym.user.entity.Apply;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDto {
    private Long id;
    private Long userId;
    private Long programId;
    private Integer count;
    private String able_time;
    private Apply.ApplyState applyState;
    private LocalDateTime createdAt;

    public static ApplyDto fromEntity(Apply entity){
        ApplyDto.ApplyDtoBuilder builder = ApplyDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .programId(entity.getProgramId())
                .count(entity.getCount())
                .able_time(entity.getAble_time())
                .applyState(entity.getApplyState())
                .createdAt(entity.getCreatedAt());
        return builder.build();
    }
}
