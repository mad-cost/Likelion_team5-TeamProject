package com.example.homeGym.instructor.service;

import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.CommentDto;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repository.CommentRepository;
import com.example.homeGym.instructor.repository.InstructorRepository;
import com.example.homeGym.instructor.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImp implements CommentService {

    private final InstructorRepository instructorRepository;
    private final CommentRepository commentRepository;
    private final AuthenticationFacade facade;

    // 댓글 작성
    @Override
    public CommentDto createReview(Long instructorId, CommentDto commentDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.USER_NOT_EXISTS);

        Instructor currentInstructor = facade.getCurrentInstructor();
        Comment instructorReview = Comment.builder()
                .content(commentDto.getContent())
                .instructor(currentInstructor)
                .instructor(optionalInstructor.get())
                .build();
        return CommentDto.fromEntity(commentRepository.save(instructorReview));
    }

    // 댓글 수정
    @Override
    public CommentDto updateReview(Long instructorId, Long reviewId, CommentDto commentDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.USER_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        Optional<Comment> optionalInstructorReview = commentRepository.findById(reviewId);
        // review가 존재하지 않을 경우
        if (optionalInstructorReview.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_NOT_EXISTS);

        Comment instructorReview = optionalInstructorReview.get();
        // instructorReview가 instructor의 댓글이 아닌 경우
        if (!instructorReview.getInstructor().getId().equals(instructorId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_MISMATCH);

        Instructor currentInstructor = facade.getCurrentInstructor();
        // 리뷰 작성한 강사가 현재 접속 유저가 아닌 경우
        if (!instructorReview.getInstructor().equals(currentInstructor))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_FORBIDDEN);

        instructorReview.setContent(commentDto.getContent());
        return CommentDto.fromEntity(commentRepository.save(instructorReview));
    }

    // 댓글 삭제
    @Override
    public void deleteReview(Long instructorId, Long reviewId) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.USER_NOT_EXISTS);
        }
        Instructor instructor = optionalInstructor.get();
        Optional<Comment> optionalInstructorReview = commentRepository.findById(reviewId);
        // review가 존재하지 않을 경우
        if (optionalInstructorReview.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_NOT_EXISTS);

        Comment instructorReview = optionalInstructorReview.get();
        // instructorReview가 instructor의 댓글이 아닌 경우
        if (!instructorReview.getInstructor().getId().equals(instructorId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_MISMATCH);

        Instructor currentInstructor = facade.getCurrentInstructor();
        if (!instructorReview.getInstructor().equals(currentInstructor))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_FORBIDDEN);
        commentRepository.deleteById(reviewId);
    }

    @Override
    public CommentDto getCommentDtoById(Long commentId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isEmpty()) {
            // 댓글이 존재하지 않을 경우 예외 처리
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_NOT_EXISTS);
        }
        return CommentDto.fromEntity(optionalComment.get());
    }

    public Comment findByReviewId(Long id){
        Comment comment = commentRepository.findByReviewId(id);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년MM월dd일");
        String changeDate = comment.getCreateAt().format(formatter);
        comment.setDateCreatedAt(changeDate);
        commentRepository.save(comment);

        return comment;
    }

}
