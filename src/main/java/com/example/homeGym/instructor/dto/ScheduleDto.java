package com.example.homeGym.instructor.dto;


import com.example.homeGym.instructor.entity.Schedule;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long id;
    private String week;
    private String time;

    private String instructorName;
    private Long instructorId;
    private LocalDateTime createAt;

    public static ScheduleDto fromEntity(Schedule entity) {

        return ScheduleDto.builder()
                .id(entity.getId())
                .week(entity.getWeek())
                .time(entity.getTime())
                .instructorName(entity.getInstructorName())
                .instructorId(entity.getInstructorId())
                .createAt(entity.getCreateAt())
                .build();
    }
}
