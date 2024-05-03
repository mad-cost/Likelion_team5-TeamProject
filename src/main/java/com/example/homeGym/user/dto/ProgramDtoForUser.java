package com.example.homeGym.user.dto;

import com.example.homeGym.instructor.entity.MainCategory;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.SubCategory;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramDtoForUser {

    private Long id;
    private Long instructorId;
    private MainCategory mainCategory;
    private SubCategory subCategory;
    private String title;
    private String description;
    private String supplies;
    private String curriculum;
    private Integer price1;
    private Integer price10;
    private Integer price20;
    private Program.ProgramState state;
    private LocalDateTime createdAt;
    private LocalDateTime approvalTime;

    public static ProgramDtoForUser fromEntity(Program entity){
        ProgramDtoForUser.ProgramDtoForUserBuilder builder = ProgramDtoForUser.builder()
                .id(entity.getId())
                .instructorId(entity.getInstructorId())
                .mainCategory(entity.getMainCategory())
                .subCategory(entity.getSubCategory())
                .title(entity.getTitle())
                .description(entity.getDescription())
                .supplies(entity.getSupplies())
                .curriculum(entity.getCurriculum())
                .price1(entity.getPrice1())
                .price10(entity.getPrice10())
                .price20(entity.getPrice20())
                .state(entity.getState())
                .createdAt(entity.getCreatedAt())
                .approvalTime(entity.getApprovalTime());
        return builder.build();
    }
}
