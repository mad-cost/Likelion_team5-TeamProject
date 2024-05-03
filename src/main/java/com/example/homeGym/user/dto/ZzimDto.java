package com.example.homeGym.user.dto;

import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.entity.Zzim;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ZzimDto {

    private Long id;
    private Long userId;
    private Long programId;
    private LocalDateTime createdAt;
    @Setter
    private Program program;
    @Setter
    private Instructor instructor;

    public static ZzimDto fromEntity(Zzim entity){
        ZzimDto.ZzimDtoBuilder builder = ZzimDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .programId(entity.getProgramId())
                .createdAt(entity.getCreatedAt());
        return builder.build();
    }
}
