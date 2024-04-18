package com.example.homeGym.user.service;

import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.entity.Review;
import com.example.homeGym.user.repository.ReviewRepository;
import com.example.homeGym.user.utils.FileHandlerUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FileHandlerUtils fileHandlerUtils;

    public ReviewDto findByUserProgramIdAndUserId(Long userProgramId, Long userId){
        if (reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId).isPresent()){
            return ReviewDto.fromEntity(reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId));
        }
        return ReviewDto.fromEntity(reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId));
    }

    public ReviewDto createReview(Long userId, Long userProgramId, MultipartFile image) throws IOException{
        String imagePath = null;
        if (image != null && !image.isEmpty()){
            imagePath = fileHandlerUtils.saveFile("review/", "review_image_", image);
        }

        Review review = new Review();
        review.setMemo("test중입니다");
        review.setStars(4);
        review.setUserId(userId);
        review.setUserProgramId(userProgramId);
        review.setImageUrl(imagePath);
        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    public void deleteReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //리뷰에 연관된 이미지 파일 경로를 가져오기.
        String imagePath = review.getImageUrl();
        System.out.println("imagePath = " + imagePath);

        //리뷰 삭제
        reviewRepository.delete(review);

        //이미지 파일이 존재하면 삭제
        if (imagePath != null){
            String mediaPath = "media/";
            String fullPath = mediaPath + imagePath.replace("/static/", "");
            try {
                Files.deleteIfExists(Path.of(fullPath));
            }catch (IOException e){
                log.error(e.getMessage());
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}
