package com.example.homeGym.user.dto;

import com.example.homeGym.user.entity.Review;
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
public class ReviewDto {

    private Long id;
    private Long userProgramId;
    private Long userId;
    private Integer stars;
    private String imageUrl;
    private String memo;
    private LocalDateTime createdAt;

    public static ReviewDto fromEntity(Review entity){
        ReviewDto.ReviewDtoBuilder builder = ReviewDto.builder()
                .id(entity.getId())
                .userProgramId(entity.getUserProgramId())
                .userId(entity.getUserId())
                .stars(entity.getStars())
                .imageUrl(entity.getImageUrl())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt());
        return builder.build();
    }

}
