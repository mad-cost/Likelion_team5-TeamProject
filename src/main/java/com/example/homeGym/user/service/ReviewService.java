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

    public ReviewDto findByUserProgramIdAndUserId(Long userProgramId, Long userId){
        if (reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId).isPresent()){
            return ReviewDto.fromEntity(reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId));
        }
        return ReviewDto.fromEntity(reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId));
    }
}
