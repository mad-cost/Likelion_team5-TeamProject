package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.ProgramCheck;
import com.example.homeGym.instructor.entity.Time;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgramCheckDto {
    private Long id;
    private Long userProgramId;
    private String memo;
    private LocalDate programDate;
    private Time time;
    private LocalDateTime createdAt;


    public static ProgramCheckDto fromEntity(ProgramCheck entity) {
        return ProgramCheckDto.builder()
                .id(entity.getId())
                .userProgramId(entity.getUserProgramId())
                .memo(entity.getMemo())
                .programDate(entity.getProgramDate())
                .time(entity.getTime())
                .build();
    }

    public ProgramCheck toEntity(){
        return ProgramCheck.builder()
                .id(this.id)
                .userProgramId(this.userProgramId)
                .memo(this.memo)
                .programDate(this.programDate)
                .time(this.time)
                .build();
    }
}
