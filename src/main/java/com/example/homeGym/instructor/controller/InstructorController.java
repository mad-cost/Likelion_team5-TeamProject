package com.example.homeGym.instructor.controller;

import com.example.homeGym.common.util.AuthenticationFacade;
import com.example.homeGym.instructor.dto.*;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.repository.InstructorRepository;
import com.example.homeGym.instructor.service.InstructorService;
import com.example.homeGym.instructor.service.ProgramService;
import com.example.homeGym.instructor.service.UserProgramService;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.service.UserService;
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
import java.util.stream.Collectors;

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

    //인증쪽에서 작성
   /* // 강사 로그인
    @GetMapping("/login")
    public String loginPage(){
        return "instructor-login";
    }

    @PostMapping("/login")
    public void login() {
    }

    // 강사 로그아웃
    @PostMapping("/logout")
    public void logout() {
    }*/


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

    //로그인 아이디 중복 검사
    @PostMapping("/check-loginId")
    @ResponseBody
    public ResponseEntity<?> checkLoginId(@RequestBody Map<String, String> request) {
        String loginId = request.get("loginId");
        boolean isAvailable = instructorService.isLoginIdAvailable(loginId);
        return ResponseEntity.ok(Map.of("isAvailable", isAvailable));
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
        return "/instructor/withdrawProposal";
    }


    @PostMapping("/withdraw")
    public String submitWithdrawForm(@ModelAttribute("withdrawal") InstructorWithdrawalDto withdrawalDto, Model model) {
        String message = instructorService.withdrawalProposal(1L, withdrawalDto.getWithdrawalReason());
        model.addAttribute("message", message);
        return "instructor/withdrawResult";
    }

    // 강사 페이지
    @GetMapping("/")
    public String InstructorPage(Model model) {
        //인증에서 강사 정보 가져오기
//        Instructor instructor = facade.getCurrentInstructor();
//        model.addAttribute("instructor", instructor);

        //임시 데이터 넣기
        model.addAttribute("profileDto", new InstructorProfileDto(
                "/static/assets/img/free-icon-lion-512px.png", "정동은", 4.2));
        return "instructor/instructor-page";
    }

    //강사 정보 수정 페이지
    @GetMapping("/profile")
    public String profileUpdatePage(Model model){
      //  Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        InstructorDto instructorDto = instructorService.findById(/*currentInstructor.getId()*/ 1L);
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
       // Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        // Fetch the instructor ID based on the username
        Page<InstructorReviewDto> reviews = instructorService.findReviewsByInstructorId(/*currentInstructor.getId()*/1L, pageable);

        model.addAttribute("reviews", reviews);
        return "instructor/instructor-reviews"; // 타임리프 템플릿 파일 이름
    }

    // 강사 수업 페이지
    @GetMapping("/program")
    public String instructorProgramList(Model model) {
        //Instructor currentInstructor = facade.getCurrentInstructor(); // 현재 로그인한 강사 정보 가져오기
        Map<String, List<ProgramDto>> programs = instructorService.findProgramsByInstructorIdSeparatedByState(1L/*currentInstructor.getId()*/);
        model.addAttribute("inProgressPrograms", programs.get("inProgress"));
        model.addAttribute("otherPrograms", programs.get("other"));
        return "instructor/program"; // 타임리프 템플릿 파일 이름
    }

    // 강사 프로그램 상세
    @GetMapping("/program/{programId}")
    public String InstructorProgramDetail(@PathVariable Long programId, Model model) {
        ProgramDto programDto = programService.findByProgramId(programId);

       /* //강사 id 와 프로그램의 주인 여부 검증
        if (!checkInstructorAccess(programId)) {
            return "redirect:/user/main";
        }*/

        List<UserProgramDto> userPrograms = userProgramService.findByProgramIdAndStateInProgress(programId);
        // 각 사용자에 대한 정보 조회
        List<UserProgramDetailDto> userDetails = userPrograms.stream()
                .map(userProgram -> {
                    UserDto user = userService.findById(userProgram.getUserId());
                    return new UserProgramDetailDto(user.getName(), user.getId(), userProgram.getCount(), userProgram.getMaxCount());
                })
                .toList();

        model.addAttribute("program", programDto);
        model.addAttribute("userDetails", userDetails);
        return "instructor/program-detail";
    }
    private boolean checkInstructorAccess(Long programId) {
        ProgramDto programDto = programService.findByProgramId(programId);
        Instructor currentInstructor = facade.getCurrentInstructor();
        return programDto.getInstructorId().equals(currentInstructor.getId());
    }

    // 강사 프로그램 회원 상세
    @GetMapping("/program/{programId}/user/{userId}")
    public String userProgramList() {

        /* //강사 id 와 프로그램의 주인 여부 검증
        if (!checkInstructorAccess(programId)) {
            return "redirect:/user/main";
        }*/

        return "instructor/program-detail-user";
    }

    // 강사 일지 작성
    @PostMapping("/program/{programId}/user/{userId}")
    public void createDaily() {

    }

    // 강사 일지 수정
    @PutMapping("/program/{programId}/user/{userId}")
    public void pathDaily() {

    }
}
