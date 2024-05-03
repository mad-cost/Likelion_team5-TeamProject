package com.example.homeGym.main.controller;

import com.example.homeGym.common.util.AuthenticationUtilService;
import com.example.homeGym.instructor.dto.InstructorDto;
import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.dto.ProgramMatchDto;
import com.example.homeGym.instructor.dto.ScheduleDto;
import com.example.homeGym.instructor.entity.*;
import com.example.homeGym.instructor.repository.CommentRepository;
import com.example.homeGym.instructor.repository.ScheduleRepository;
import com.example.homeGym.instructor.service.*;
import com.example.homeGym.user.dto.ReviewDto;
import com.example.homeGym.user.entity.Apply;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.service.ApplyService;
import com.example.homeGym.user.service.ReviewService;
import com.example.homeGym.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("main")
public class MainController {
    private final ProgramService programService;
    private final InstructorService instructorService;
    private final UserProgramService userProgramService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final CommentServiceImp commentServiceImp;
    private final ScheduleService scheduleService;
    private final ApplyService applyService;
    private final AuthenticationUtilService authenticationUtilService;


    //  프로그램 소개 페이지
    @GetMapping("/introduce/program/{programId}")
    public String introduce(
            @PathVariable("programId")
            Long programId,
            Model model
    ) {

        Program program = programService.findById(programId);
        ProgramDto programDto = programService.findByProgramIdPlusCategory(programId);

        model.addAttribute("program", programDto);

//      Program의 instructorId를 가져와서 InstructorDto 가져오기
        Long instructorId = program.getInstructorId();
        InstructorDto instructorDto = instructorService.findById(instructorId);
        model.addAttribute("instructor", instructorDto);

//      강사 스케줄 리스트
        List<ScheduleDto> scheduleDto = scheduleService.getAllSchedulesByInstructorId(instructorId);
        model.addAttribute("schedules", scheduleDto);

//      programId에 해당하는 모든 user_program을 가져오고 user_program의 id로 리뷰 전부 가져오기
//      programId에 해당하는 모든 user_program의 id가져오기
        List<UserProgram> userPrograms = userProgramService.findAllByProgramIdConvertId(programId);
//      user_program들의 id 해당하는 리뷰 전부 가져오기
        List<ReviewDto> programReviews = reviewService.findAllByUserProgramIdConvertId(userPrograms);
        for (ReviewDto dto : programReviews) {
//        dto에 User 넣어주기
            Long userId = dto.getUserId();
            dto.setUser(userService.findByLongId(userId));
//        dto에 Comment 넣어주기
            Comment comment = commentServiceImp.findByReviewId(dto.getId());
            dto.setComment(comment);
        }

        model.addAttribute("reviews", programReviews);

        // 스케줄 정보를 데이터베이스에서 가져옵니다
        List<ScheduleDto> schedules = scheduleService.getAllSchedules(instructorId);

        // 스케줄 정보를 모델에 추가합니다
        model.addAttribute("schedules", schedules);


        return "introduce";
    }

    @GetMapping("/introduce/program/all/{programId}")
    @ResponseBody
    public List<ScheduleDto> getAllSchedules(
            @PathVariable("programId")
            Long programId,
            Model model) {
        Program program = programService.findById(programId);

//      Program의 instructorId를 가져와서 InstructorDto 가져오기
        Long instructorId = program.getInstructorId();
        return scheduleService.getAllSchedules(instructorId);
    }


    @PostMapping("/introduce/program/{programId}/apply")
    public String apply(
            @PathVariable("programId")
            Long programId,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes,
            Authentication authentication
    ) {
        String week = request.getParameter("week");
        String time = request.getParameter("time");
        String count = request.getParameter("count");

        // 요일, 시간, 회차권을 모두 선택했는지 확인합니다.
        if (week == null || time == null || count == null || week.isEmpty() || time.isEmpty() || count.isEmpty()) {
            // 선택하지 않은 경우, 에러 메시지를 추가하고 다시 프로그램 소개 페이지로 리다이렉션합니다.
            redirectAttributes.addFlashAttribute("error", "요일, 시간, 회차권을 모두 선택해주세요.");
            return "redirect:/main/introduce/program/" + programId;
        }
        Long userId = authenticationUtilService.getId(authentication);
        applyService.saveApply(week, time, count, userId, programId );

        return "redirect:/main/introduce/program/" + programId;
    }


    @GetMapping("/match")
    public String match(
            Model model
    ) {
        List<Instructor> instructors = instructorService.findAll();
        //  List<ProgramDto> programs = new ArrayList<>();
        List<ProgramMatchDto> programMatchDtos = new ArrayList<>();
        //  model.addAttribute("programs", programs);
        model.addAttribute("programMatchDtos", programMatchDtos);
        model.addAttribute("programMatch", new ProgramMatchDto());
        model.addAttribute("ex", instructors);

        return "match";
    }

    @PostMapping("/match/search")
    @ResponseBody
    public List<ProgramMatchDto> filterPrograms(
            @RequestParam("siDo") String siDo,
            @RequestParam("siGunGu") String siGunGu,
            @RequestParam("dong") String dong,
            @RequestParam("mainCategoryId") Integer mainCategoryId,
            @RequestParam("subCategoryId") Integer subCategoryId,
            Model model
    ) {
        List<ProgramMatchDto> programMatchDtos = programService.findProgramsByFilters(siDo, siGunGu, dong, mainCategoryId, subCategoryId);
        return programMatchDtos;
    }

}
