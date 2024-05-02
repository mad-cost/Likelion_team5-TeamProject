package com.example.homeGym.instructor.dto;

import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramMatchDto {
    private Long id;
    private Long instructorId;
    private String instructorName;  // 강사 이름
    private String instructorProfileImageUrl;  // 강사 프로필 이미지 URL

    private String title;
    private String description;
    private Long mainCategoryId;
    private String mainCategoryName;
    private Long subCategoryId;
    private String subCategoryName;



    // 기타 필드 생략

    public static ProgramMatchDto fromEntity(Program entity, Instructor instructor) {
        return ProgramMatchDto.builder()
                .id(entity.getId())
                .instructorId(entity.getInstructorId())
                .instructorName(instructor.getName())  // 강사 이름 설정
                .instructorProfileImageUrl(instructor.getProfileImageUrl().get(0))  // 강사 프로필 이미지 URL 설정
                .title(entity.getTitle())
                .description(entity.getDescription())
                .mainCategoryId(entity.getMainCategory().getId())
                .mainCategoryName(entity.getMainCategory().getName())
                .subCategoryId(entity.getSubCategory().getId())
                .subCategoryName(entity.getSubCategory().getName())
                // 기타 필드 설정
                .build();
    }
}
