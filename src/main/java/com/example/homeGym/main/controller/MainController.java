package com.example.homeGym.main.controller;

import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.entity.Comment;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.CommentRepository;
import com.example.homeGym.instructor.service.CommentServiceImp;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.service.ReviewService;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
  private final ProgramService programService;
  private final InstructorService instructorService;
  private final UserProgramService userProgramService;
  private final ReviewService reviewService;
  private final UserService userService;
  private final CommentServiceImp commentServiceImp;

//  프로그램 소개 페이지
  @GetMapping("/introduce/{programId}")
  public String introduce(
          @PathVariable ("programId")
          Long programId,
          Model model
  ){
      Program program = programService.findById(programId);

      ProgramDto programDto = programService.findByProgramId(programId);
      model.addAttribute("program", programDto);

//      Program의 instructorId를 가져와서 InstructorDto 가져오기
      Long instructorId = program.getInstructorId();
      InstructorDto instructorDto = instructorService.findById(instructorId);
      model.addAttribute("instructor", instructorDto);

//      programId에 해당하는 모든 user_program을 가져오고 user_program의 id로 리뷰 전부 가져오기
//      programId에 해당하는 모든 user_program의 id가져오기
      List<UserProgram> userPrograms = userProgramService.findAllByProgramIdConvertId(programId);
//      user_program들의 id 해당하는 리뷰 전부 가져오기
      List<ReviewDto> programReviews = reviewService.findAllByUserProgramIdConvertId(userPrograms);
      for (ReviewDto dto : programReviews){
//        dto에 User 넣어주기
        Long userId = dto.getUserId();
        dto.setUser(userService.findByLongId(userId));
//        dto에 Comment 넣어주기
        Comment comment = commentServiceImp.findByReviewId(dto.getId());
        dto.setComment(comment);
      }

      model.addAttribute("reviews", programReviews);

      return "introdeuce";
  }



}
