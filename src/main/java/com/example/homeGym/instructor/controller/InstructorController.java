package com.example.homeGym.instructor.controller;

import com.example.homeGym.auth.dto.SignInDto;
import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.*;
import com.example.homeGym.instructor.entity.Instructor;

import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.InstructorRepository;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramCheckService;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("instructor")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;
    private final UserService userService;
    private final UserProgramService userProgramService;
    private final InstructorRepository instructorRepository;
    private final AuthenticationFacade facade;
    private final ProgramService programService;
    private final ProgramCheckService programCheckService;

    //인증쪽에서 작성
    // 강사 로그인
    @GetMapping("/signin")
    public String loginPage(){
        return "instructor/instructor-signin";
    }

    @PostMapping("/signin")
    public String login(HttpServletResponse res, @ModelAttribute SignInDto signInDto) throws Exception {

        boolean login = instructorService.signIn(res, signInDto.getEmail(), signInDto.getPassword());

        return "redirect:/user/main";
    }

    // 강사 로그아웃
    @PostMapping("/logout")
    public void logout() {
    }


    // 강사 신청
    @GetMapping("/proposal")
    public String proposalPage(Model model){
        model.addAttribute("InstructorCreateDto", new InstructorCreateDto());
        return "/instructor/proposal";
    }
    @PostMapping("/proposal")
    public String proposal(@ModelAttribute InstructorCreateDto instructorCreateDto) {
        log.info("Creating instructor with name: {}", instructorCreateDto.getName());
        instructorService.createInstructor(instructorCreateDto);
        return "redirect:/instructor/proposal/success";
    }
    @GetMapping("/proposal/success")
    public String proposalSuccessPage(){
        return "/instructor/proposal-success";
    }


    // 이메일 중복 검사
    @PostMapping("/check-email")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean isAvailable = instructorService.isEmailAvailable(email);
        return ResponseEntity.ok(Map.of("isAvailable", isAvailable));
    }


    @GetMapping("/withdraw")
    public String showWithdrawForm(Model model) {
        model.addAttribute("withdrawal", new InstructorWithdrawalDto());
        return "instructor/withdrawProposal";
    }


    @PostMapping("/withdraw")
    public String submitWithdrawForm(@ModelAttribute("withdrawal") InstructorWithdrawalDto withdrawalDto, Model model) {
        Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        String message = instructorService.withdrawalProposal(currentInstructor.getId(), withdrawalDto.getWithdrawalReason());
        model.addAttribute("message", message);
        return "instructor/withdrawResult";
    }

    // 강사 페이지
    @GetMapping("/")
    public String InstructorPage(Model model) {
        //인증에서 강사 정보 가져오기
        Instructor instructor = facade.getCurrentInstructor();
        model.addAttribute("profileDto",
                new InstructorProfileDto(instructor.getProfileImageUrl(), instructor.getName(), instructor.getRating()));
        return "instructor/instructor-page";
    }

    //강사 정보 수정 페이지
    @GetMapping("/profile")
    public String profileUpdatePage(Model model){
        Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        InstructorDto instructorDto = instructorService.findById(currentInstructor.getId());
        InstructorUpdateDto updateDto = new InstructorUpdateDto();

        model.addAttribute("instructorDto", instructorDto);
        model.addAttribute("updateDto", updateDto);
        return "instructor/instructor-update";
    }

    // 강사 정보 수정
    @PutMapping("/profile")
    public String updateProfile(@ModelAttribute InstructorUpdateDto updateDto, RedirectAttributes attributes) {
        try {
            instructorService.updateInstructor(updateDto);
            attributes.addFlashAttribute("message", "강사 정보가 업데이트 되었습니다.");
            return "redirect:/instructor/update-result";
        } catch (Exception e) {
            attributes.addFlashAttribute("message", "업데이트 실패: " + e.getMessage());
            return "redirect:/instructor/update-result";
        }
    }

    @GetMapping("/update-result")
    public String updateResultPage(){
        return "instructor/update-result";
    }


    // 로그인한 강사의 리뷰 페이지
    @GetMapping("/reviews")
    public String getInstructorReviews(@PageableDefault(size = 10)Pageable pageable, Model model) {
        Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        // Fetch the instructor ID based on the username
        Page<InstructorReviewDto> reviews = instructorService.findReviewsByInstructorId(currentInstructor.getId(), pageable);

        model.addAttribute("reviews", reviews);
        return "instructor/instructor-reviews"; // 타임리프 템플릿 파일 이름
    }
             
    // 강사 수업 페이지
    @GetMapping("/program")
    public String instructorProgramList(Model model) {
        Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        Map<String, List<ProgramDto>> programs = instructorService.findProgramsByInstructorIdSeparatedByState(currentInstructor.getId());
        model.addAttribute("inProgressPrograms", programs.get("inProgress"));
        model.addAttribute("otherPrograms", programs.get("other"));
        return "instructor/program"; // 타임리프 템플릿 파일 이름
    }

    // 강사 프로그램 상세
    @GetMapping("/program/{programId}")
    public String InstructorProgramDetail(@PathVariable Long programId, Model model) {
        ProgramDto programDto = programService.findByProgramId(programId);

        //강사 id 와 프로그램의 주인 여부 검증
        if (!checkInstructorAccess(programId)) {
            return "redirect:/user/main";
        }

        List<UserProgramDto> userPrograms = userProgramService.findByProgramIdAndStateInProgress(programId);
        List<UserProgramDto> finishUserPrograms = userProgramService.findByProgramIdAndStateFINISH(programId);
        // 각 사용자에 대한 정보 조회 (진행중 유저, 종료된 유저)
        List<UserProgramDetailDto> userDetails = userPrograms.stream()
                .map(userProgram -> {
                    UserDto user = userService.findById(userProgram.getUserId());
                    return new UserProgramDetailDto(user.getName(), user.getId(), userProgram.getCount(), userProgram.getMaxCount());
                })
                .toList();
        List<UserProgramDetailDto> finishUserDetails = finishUserPrograms.stream()
                .map(userProgram -> {
                    UserDto user = userService.findById(userProgram.getUserId());
                    return new UserProgramDetailDto(user.getName(), user.getId(), userProgram.getCount(), userProgram.getMaxCount());
                })
                .toList();

        model.addAttribute("program", programDto);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("finishUserDetails", finishUserDetails);
        return "instructor/program-detail";
    }
    private boolean checkInstructorAccess(Long programId) {
        ProgramDto programDto = programService.findByProgramId(programId);
        Instructor currentInstructor = facade.getCurrentInstructor();
        return programDto.getInstructorId().equals(currentInstructor.getId());
    }

    // 강사 프로그램 회원 상세
    @GetMapping("/program/{programId}/user/{userId}")
    public String userProgramList(@PathVariable Long programId, @PathVariable Long userId, Model model) {

         //강사 id 와 프로그램의 주인 여부 검증
        if (!checkInstructorAccess(programId)) {
            return "redirect:/user/main";
        }
        UserProgram userProgram = userProgramService.findByUserIdAndProgramId(userId, programId);
        List<ProgramCheckDto> programCheckDtoList = programCheckService.getAllProgramChecksByUserProgramId(userProgram.getId());

        model.addAttribute("programChecks", programCheckDtoList);
        model.addAttribute("userProgramId", userProgram.getId());
        return "instructor/program-detail-user";
    }

    // 강사 일지 작성
    @PostMapping("/program/{programId}/user/{userId}")
    public String createDaily(
            @ModelAttribute ProgramCheckDto programCheckDto,
            @PathVariable Long programId,
            @PathVariable Long userId) {
        UserProgram userProgram = userProgramService.findByUserIdAndProgramId(userId, programId);
        programCheckDto.setUserProgramId(userProgram.getId());
        programCheckService.createProgramCheck(programCheckDto);
        return "redirect:/instructor/program/" + programId;

    }

    // 강사 일지 수정
    @PutMapping("/program/{programId}/user/{userId}/{programCheckId}")
    public String pathDaily(
            @ModelAttribute ProgramCheckDto programCheckDto,
            @PathVariable Long programId,
            @PathVariable Long userId,
            @PathVariable Long programCheckId
    ) {
        programCheckService.updateProgramCheck(programCheckId, programCheckDto);
        return "redirect:/instructor/program/" + programId + "/user/" + userId;
    }
}
