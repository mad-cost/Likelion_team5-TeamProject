package com.example.homeGym.user.dto;

import com.example.homeGym.instructor.entity.Comment;
import com.example.homeGym.user.entity.Review;
import com.example.homeGym.user.entity.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    private List<String> imageUrl;
    private String memo;
    private LocalDateTime createdAt;
    @Setter
    private User user;
    @Setter
    private Comment comment;
    @Setter
    private String dateCreatedAt; // yyyy년MM월dd일

    public static ReviewDto fromEntity(Review entity){
        ReviewDto.ReviewDtoBuilder builder = ReviewDto.builder()
                .id(entity.getId())
                .userProgramId(entity.getUserProgramId())
                .userId(entity.getUserId())
                .stars(entity.getStars())
                .imageUrl(entity.getImageUrl())
                .memo(entity.getMemo())
                .createdAt(entity.getCreatedAt())
                .dateCreatedAt(entity.getDateCreatedAt());
        return builder.build();
    }

    public static ReviewDto fromEntity(Optional<Review> entity){
        if (entity.isPresent()) {
            Review review = entity.get();
            return ReviewDto.builder()
                    .id(review.getId())
                    .userProgramId(review.getUserProgramId())
                    .userId(review.getUserId())
                    .stars(review.getStars())
                    .imageUrl(review.getImageUrl())
                    .memo(review.getMemo())
                    .createdAt(review.getCreatedAt())
                    .build();
        }else {
            return ReviewDto.builder().build();
        }
    }

}
