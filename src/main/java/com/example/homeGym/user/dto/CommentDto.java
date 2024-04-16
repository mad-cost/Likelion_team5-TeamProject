package com.example.homeGym.user.dto;

import com.example.homeGym.user.entity.Comment;
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
public class CommentDto {
    private Long id;
    private Long reviewId;
    private String memo;
    private LocalDateTime createAt;

    public static CommentDto fromEntity(Comment entity){
        CommentDto.CommentDtoBuilder builder = CommentDto.builder()
                .id(entity.getId())
                .reviewId(entity.getReviewId())
                .memo(entity.getMemo())
                .createAt(entity.getCreateAt());
        return builder.build();
    }
}
