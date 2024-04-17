package com.example.homeGym.instructor.dto;


import com.example.homeGym.instructor.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class CommentDto {
    private Long id;

    @Setter
    private String content;

    public static CommentDto fromEntity(Comment entity) {
        CommentDto dto = new CommentDto();
        dto.id = entity.getId();
        return dto;
    }
}
