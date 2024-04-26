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
    public CommentDto createReview(Long instructorId, Long reviewId, CommentDto commentDto) {
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.USER_NOT_EXISTS);
        Instructor instructor = optionalInstructor.get();

        Comment instructorReview = Comment.builder()
                .content(commentDto.getContent())
                .reviewId(reviewId)
                .instructor(instructor)
                .build();
        return CommentDto.fromEntity(commentRepository.save(instructorReview));
    }

    // 댓글 수정
    @Override
    public CommentDto updateReview(Long instructorId, Long reviewId, CommentDto commentDto) {
        log.info("Updating comment with ID: {}, Instructor ID: {}, Review ID: {}, Content: {}", commentDto.getId(), instructorId, reviewId, commentDto.getContent());
        Optional<Instructor> optionalInstructor = instructorRepository.findById(instructorId);
        // instructor가 존재하지 않을 경우
        if (optionalInstructor.isEmpty()) {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.USER_NOT_EXISTS);
        }
        log.info("Updating comment with ID: {}, Instructor ID: {}, Review ID: {}", commentDto.getId(), instructorId, reviewId);

        Instructor instructor = optionalInstructor.get();
        Optional<Comment> optionalComment = commentRepository.findById(commentDto.getId());

        // review가 존재하지 않을 경우
        if (optionalComment.isEmpty())
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_NOT_EXISTS);
        log.info("Updating comment with ID: {}, Instructor ID: {}, Review ID: {}", commentDto.getId(), instructorId, reviewId);

        Comment comment = optionalComment.get();
        // instructorReview가 instructor의 댓글이 아닌 경우
        if (!comment.getInstructor().getId().equals(instructorId))
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.COMMENT_MISMATCH);
        log.info("Updating comment with ID: {}, Instructor ID: {}, Review ID: {}", commentDto.getId(), instructorId, reviewId);

        comment.setContent(commentDto.getContent());

        log.info("Updating comment with ID: {}, Instructor ID: {}, Review ID: {}", commentDto.getId(), instructorId, reviewId);

        return CommentDto.fromEntity(commentRepository.save(comment));
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
