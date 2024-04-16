package com.example.homeGym.user.service;

import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewDto findByIdAndUserId(Long userProgramId, Long userId){
        return ReviewDto.fromEntity(reviewRepository.findByIdAndUserId(userProgramId, userId));
    }
}
