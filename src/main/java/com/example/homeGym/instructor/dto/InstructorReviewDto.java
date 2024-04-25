package com.example.homeGym.instructor.dto;

import com.example.homeGym.user.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorReviewDto {
    private Long id;
    private String userName;
    private String memo;
    private Integer stars;
    private LocalDateTime reviewDate;
    private String comment; // 강사의 답글
    private Long commentId;
    private List<String> imageUrls; // 후기와 함께 제공된 이미지 URL 목록


}
