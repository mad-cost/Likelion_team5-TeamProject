package com.example.homeGym.instructor.dto;


import com.example.homeGym.instructor.entity.Comment;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    @Setter
    private String dateCreatedAt;


    public static CommentDto fromEntity(Comment entity) {
        return CommentDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .dateCreatedAt(entity.getDateCreatedAt())
                .build();
    }
}
