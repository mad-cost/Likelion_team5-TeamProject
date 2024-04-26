package com.example.homeGym.instructor.dto;


import com.example.homeGym.instructor.entity.Comment;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private Long reviewId;
    private Long instructorId;


    public static CommentDto fromEntity(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .reviewId(entity.getReviewId())
                .instructorId(entity.getInstructor().getId())
                .build();
    }
}
