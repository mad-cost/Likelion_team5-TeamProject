package com.example.homeGym.instructor.service;

import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.InstructorReviewDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repo.InstructorRepository;
import com.example.homeGym.instructor.entity.InstructorReview;
import com.example.homeGym.instructor.repo.InstructorReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorReviewServiceImp implements InstructorReviewService{

    private final InstructorRepository instructorRepository;
    private final InstructorReviewRepository instructorReviewRepository;
    private final AuthenticationFacade facade;

    // 댓글 작성
    @Override
    public InstructorReviewDto createReview(Long instructorId, InstructorReviewDto instructorReviewDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존제하지 않을 경우
        if (optionalInstructor.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_NOT_EXISTS);

        Instructor currentInstructor = facade.getCurrentInstructor();
        InstructorReview instructorReview = InstructorReview.builder()
                .content(instructorReviewDto.getContent())
                .instructor(currentInstructor)
                .instructor(optionalInstructor.get())
                .build();
        return InstructorReviewDto.fromEntity(instructorReviewRepository.save(instructorReview));
    }

    // 댓글 수정
    @Override
    public InstructorReviewDto updateReview(Long instructorId, Long instructorReviewId, InstructorReviewDto instructorReviewDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        Optional<InstructorReview> optionalInstructorReview = instructorReviewRepository.findById(instructorReviewId);
        // review가 존재하지 않을 경우
        if (optionalInstructorReview.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_REVIEW_NOT_EXISTS);

        InstructorReview instructorReview = optionalInstructorReview.get();
        // instructorReview가 instructor의 댓글이 아닌 경우
        if (!instructorReview.getInstructor().getId().equals(instructorId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_REVIEW_MISMATCH);

        Instructor currentInstructor = facade.getCurrentInstructor();
        // 리뷰 작성한 강사가 현재 접속 유저가 아닌 경우
        if (!instructorReview.getInstructor().equals(currentInstructor))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_REVIEW_FORBIDDEN);

        instructorReview.setContent(instructorReviewDto.getContent());
        return InstructorReviewDto.fromEntity(instructorReviewRepository.save(instructorReview));
    }

    // 댓글 삭제
    @Override
    public void deleteReview(Long instructorId, Long instructorReviewId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        Optional<InstructorReview> optionalInstructorReview = instructorReviewRepository.findById(instructorReviewId);
        // review가 존재하지 않을 경우
        if (optionalInstructorReview.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_REVIEW_NOT_EXISTS);

        InstructorReview instructorReview = optionalInstructorReview.get();
        // instructorReview가 instructor의 댓글이 아닌 경우
        if (!instructorReview.getInstructor().getId().equals(instructorId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_REVIEW_MISMATCH);

        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!instructorReview.getInstructor().equals(currentInstructor))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.INSTRUCTOR_REVIEW_FORBIDDEN);
        instructorReviewRepository.deleteById(instructorReviewId);
    }
}
