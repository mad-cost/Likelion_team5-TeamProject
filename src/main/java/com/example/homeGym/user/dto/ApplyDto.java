package com.example.homeGym.user.dto;

import com.example.homeGym.instructor.entity.Time;
import com.example.homeGym.instructor.entity.Week;
import com.example.homeGym.user.entity.Apply;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyDto {
    private Long id;
    private Long userId;
    //필요 유저 정보
    private String name;
    private String email;
    //필요한 프로그램 정보
    private Long programId;
    private String programTitle;

    private Integer count;
    private Week able_week;
    private Time able_time;
    private Apply.ApplyState applyState;

    private LocalDateTime createdAt;

    public static ApplyDto fromEntity(Apply entity, String name, String email, String programTitle){
        ApplyDto.ApplyDtoBuilder builder = ApplyDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .name(name)
                .email(email)
                .programId(entity.getProgramId())
                .programTitle(programTitle)
                .count(entity.getCount())
                .able_week(entity.getAble_week())
                .able_time(entity.getAble_time())
                .applyState(entity.getApplyState())
                .createdAt(entity.getCreatedAt());
        return builder.build();
    }
}
