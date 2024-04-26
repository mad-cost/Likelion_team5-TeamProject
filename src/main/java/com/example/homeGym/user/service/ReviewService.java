package com.example.homeGym.user.service;

import com.example.homeGym.admin.dto.SettlementDto;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.entity.Review;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.ReviewRepository;
import com.example.homeGym.user.utils.FileHandlerUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final FileHandlerUtils fileHandlerUtils;

    public List<ReviewDto> findByUserProgramIdAndUserId(Long userProgramId, Long userId){
//        if (reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId).isPresent()){
//
//            return ReviewDto.fromEntity(reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId));
//        }
//        return ReviewDto.fromEntity(reviewRepository.findByUserProgramIdAndUserId(userProgramId, userId));
        List<ReviewDto> reviewDtos = new ArrayList<>();
        for (Review review : reviewRepository.findAllByUserProgramIdAndUserId(userProgramId, userId)){
            reviewDtos.add(ReviewDto.fromEntity(review));
        }
        return reviewDtos;

    }

    public ReviewDto createReview(Long userId, Long userProgramId, List<MultipartFile> images, Integer rating, String memo) throws IOException{
        List<String> imagePaths = new ArrayList<>();
        int count = 0;
        for (MultipartFile image :
                images) {
            if (image.getSize() != 0){
                String imgPath = fileHandlerUtils.saveFile("review",
                        String.format("review_image_user_%d_program_%d_%d", userId, userProgramId, count), image);
                imagePaths.add(imgPath);
                count ++;
            }
        }

        Review review = new Review();
        review.setMemo(memo);
        review.setStars(rating);
        review.setUserId(userId);
        review.setUserProgramId(userProgramId);
        review.setImageUrl(imagePaths);
        return ReviewDto.fromEntity(reviewRepository.save(review));
    }

    @Transactional
    public void deleteReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //리뷰에 연관된 이미지 파일 경로를 가져오기.
        List<String> imagePaths = review.getImageUrl();

        //리뷰 삭제
        reviewRepository.delete(review);

        //이미지 파일이 존재하면 삭제
        if (!imagePaths.isEmpty()){
            for (String imagePath :
                    imagePaths) {
                String mediaPath = "media/";
                String fullPath = mediaPath + imagePath.replace("/static/", "");
                try{
                    Files.deleteIfExists(Path.of(fullPath));
                }catch (IOException e){
                    log.error(e.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }
    }


    public ReviewDto updateReview(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return ReviewDto.fromEntity(review);

    }

    @Transactional
    public void updateReview(Long userId, Long reviewId, List<MultipartFile> images, Integer rating, String memo){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //저장된 사진 삭제
        //리뷰에 연관된 이미지 파일 경로를 가져오기.
        List<String> imagePaths = review.getImageUrl();

        //이미지 파일이 존재하면 삭제
        if (!imagePaths.isEmpty()){
            for (String imagePath :
                    imagePaths) {
                String mediaPath = "media/";
                String fullPath = mediaPath + imagePath.replace("/static/", "");
                try{
                    Files.deleteIfExists(Path.of(fullPath));
                }catch (IOException e){
                    log.error(e.getMessage());
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        List<String> newImagePaths = new ArrayList<>();
        Long userProgramId = review.getUserProgramId();
        int count = 0;
        if (images != null) {
            for (MultipartFile image :
                    images) {
                if (image.getSize() != 0){
                    String imgPath = fileHandlerUtils.saveFile("review",
                            String.format("review_image_user_%d_program_%d_%d", userId, userProgramId, count), image);
                    newImagePaths.add(imgPath);
                    count ++;
                }
            }
        }

        review.setMemo(memo);
        review.setStars(rating);
        review.setUserId(userId);
        review.setUserProgramId(userProgramId);
        review.setImageUrl(newImagePaths);
        ReviewDto.fromEntity(reviewRepository.save(review));

    }

    public Long getUserProgramId(Long reviewId){
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND));

        return review.getUserProgramId();
    }
//    MainController에서 사용
//    user_program의 id에 해당하는 모든 리뷰 가져오기
    public List<ReviewDto> findAllByUserProgramIdConvertId(List<UserProgram> userPrograms){
        List<ReviewDto> reviewDto = new ArrayList<>();
        for (UserProgram userProgram : userPrograms){
//            user_program의 id를 통해서 리뷰 id 가져오기
            Review reviews = reviewRepository.findByUserProgramId(userProgram.getId());
            if (reviews != null){
                //        날짜를 yyyy-mm-dd 모습으로 바꿔주기
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
                String changeDate = reviews.getCreatedAt().format(formatter);
                reviews.setDateCreatedAt(changeDate);
                reviewRepository.save(reviews);

                reviewDto.add(ReviewDto.fromEntity(reviews));
            }else {
                log.info("[ReviewService]review is not found");
            }
        }
        return reviewDto;
    }
}
