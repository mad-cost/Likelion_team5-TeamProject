package com.example.homeGym.instructor.dto;


import com.example.homeGym.instructor.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class InstructorReviewDto {
    private Long id;

    @Setter
    private String content;

    public static InstructorReviewDto fromEntity(Comment entity) {
        InstructorReviewDto dto = new InstructorReviewDto();
        dto.id = entity.getId();
        return dto;
    }
}
