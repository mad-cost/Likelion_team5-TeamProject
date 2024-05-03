package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.user.dto.UserDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProgramDto {

    private Long id;
    private UserProgram.UserProgramState state;
    private Integer count;
    private Integer maxCount;
    private Integer amount;
    private LocalDateTime createAt;
    private LocalDateTime endTime;
    private Long programId;
    private Long userId;
    private Program program;
    private Instructor instructor;

    public static UserProgramDto fromEntity(UserProgram entity){
        UserProgramDto.UserProgramDtoBuilder builder = UserProgramDto.builder()
                .id(entity.getId())
                .state(entity.getState())
                .count(entity.getCount())
                .maxCount(entity.getMaxCount())
                .amount(entity.getAmount())
                .createAt(entity.getCreateAt())
                .endTime(entity.getEndTime())
                .programId(entity.getProgramId())
                .userId(entity.getUserId());
        return builder.build();
    }
}
