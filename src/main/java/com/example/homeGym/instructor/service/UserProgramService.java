package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.ProgramDto;
import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProgramService {
    private final UserProgramRepository userProgramRepository;

    public List<UserProgramDto> findByUserIdAndStateInProgress(Long userId){
        List<UserProgram> lists = userProgramRepository.findByUserIdAndState(userId, UserProgram.UserProgramState.IN_PROGRESS);
        List<UserProgramDto> userProgramDtoList = new ArrayList<>();
        for (UserProgram userProgram: lists) {
            userProgramDtoList.add(UserProgramDto.fromEntity(userProgram));
        }
//        System.out.println("userProgramDtoList = " + userProgramDtoList);
        return userProgramDtoList;
    }

    public List<UserProgramDto> findByProgramIdAndStateInProgress(Long programId){
        List<UserProgram> lists = userProgramRepository.findByProgramIdAndState(programId, UserProgram.UserProgramState.IN_PROGRESS);
        List<UserProgramDto> userProgramDtoList = new ArrayList<>();
        for (UserProgram userProgram: lists){
            userProgramDtoList.add(UserProgramDto.fromEntity(userProgram));
        }
        return userProgramDtoList;
    }
    public List<UserProgramDto> findByProgramIdAndStateFINISH(Long programId){
        List<UserProgram> lists = userProgramRepository.findByProgramIdAndState(programId, UserProgram.UserProgramState.FINISH);
        List<UserProgramDto> userProgramDtoList = new ArrayList<>();
        for (UserProgram userProgram: lists){
            userProgramDtoList.add(UserProgramDto.fromEntity(userProgram));
        }
        return userProgramDtoList;
    }

    public List<UserProgramDto> findByUserIdAndStateFINISH(Long userId){
        List<UserProgram> lists = userProgramRepository.findByUserIdAndState(userId, UserProgram.UserProgramState.FINISH);
        List<UserProgramDto> userProgramDtoList = new ArrayList<>();
        for (UserProgram userProgram: lists) {
            userProgramDtoList.add(UserProgramDto.fromEntity(userProgram));
        }
//        System.out.println("userProgramDtoList = " + userProgramDtoList);
        return userProgramDtoList;
    }

    public UserProgramDto findById(Long userProgramId){
        return UserProgramDto.fromEntity(userProgramRepository.findById(userProgramId).orElseThrow());
    }

    public List<Long> findAllByUserIdConvertId(Long userId){
        List<Long> result = new ArrayList<>();
//        userProgram에서 userId가져오기
        for (UserProgram userProgram : userProgramRepository.findAllByUserId(userId)){
            if (userProgram.getState().equals(UserProgram.UserProgramState.IN_PROGRESS)){
//                가져온 userId의 userProgram의 Id값 result에 담아주기
                result.add(userProgram.getId());
            }else continue;
        }
        return result;
    }

    public List<UserProgramDto> findByIds(List<Long> id){
        List<UserProgramDto> userProgramDtos = new ArrayList<>();
        for (UserProgram userProgram : userProgramRepository.findAllById(id)){
                userProgramDtos.add(UserProgramDto.fromEntity(userProgram));
        }
        return userProgramDtos;
    }

    // 프로그램 삭제 시 관련 수강생 확인
    public boolean hasEnrolledUsers(Long programId) {
        List<UserProgram> userPrograms = userProgramRepository.findByProgramId(programId);
        return !userPrograms.isEmpty();
    }

    public void userCountUpdate(List<Long> userProgramsId, Long programId, Integer count){ //1, 3 / 3/ 14
        List<UserProgram> userProgramIds = userProgramRepository.findAllById(userProgramsId);
        for (UserProgram userProgram : userProgramIds){
            if (userProgram.getProgramId().equals(programId)){
                userProgram.setCount(count);
                UserProgramDto.fromEntity(userProgramRepository.save(userProgram));
                break;
            }
            else continue;
        }
    }

    public List<Integer> totalAmount(List<Long> programLongId){
        List<Integer> result = new ArrayList<>();
//        user_program에서 programId에 해당하는 Id값 하나씩 가져오기
        for (Long programId : programLongId){
            Integer sumAmount = 0;
            List<UserProgram> userPrograms = userProgramRepository.findAllByProgramId(programId);
            for (UserProgram userProgramId : userPrograms) {
//                가져온 id값의 state가 FINISH일 경우 값 저장
                if (userProgramId.getState() == UserProgram.UserProgramState.FINISH) {
                    sumAmount += userProgramId.getAmount();
                }
            }
            result.add(sumAmount);
        }
        return  result;
    }

    public List<Integer> monthAmount(List<Long> programLongId){
        List<Integer> result = new ArrayList<>();

        // 현재 날짜와 시간
        LocalDateTime now = LocalDateTime.now();
        // 현재 년도와 월
        int currentYear = now.getYear();
        Month currentMonth = now.getMonth();

//        user_program에서 programId에 해당하는 Id값 하나씩 가져오기
        for (Long programId : programLongId){
            Integer sumAmount = 0;
            List<UserProgram> userPrograms = userProgramRepository.findAllByProgramId(programId);
            for (UserProgram userProgramId : userPrograms) {
//                endTime을 통해 날짜를 비교해서 이번달 금액 가져오기
                LocalDateTime endTime = userProgramId.getEndTime();
                  if (endTime!=null) {
                      //                endTime의 년도와 월 변수에 저장하기
                      int endTimeYear = endTime.getYear();
                      Month endTimeMonth = endTime.getMonth();
                      if (currentYear == endTimeYear && currentMonth == endTimeMonth) {
                          //                가져온 id값의 state가 FINISH일 경우 값 저장
                          if (userProgramId.getState() == UserProgram.UserProgramState.FINISH) {
                              sumAmount += userProgramId.getAmount();
                          }
                      } else continue;
                  }
            }
            result.add(sumAmount);
        }
        return  result;
    }
//  유저 프로그램 전액 환불
    public void allRefund(Long userProgramId){
        UserProgram userProgram = userProgramRepository.findById(userProgramId).orElseThrow();
        userProgram.setState(UserProgram.UserProgramState.CANCEL);
        userProgramRepository.save(userProgram);
        log.info("@@@@@@Status : {}", userProgram.getState());
        UserProgramDto.fromEntity(userProgram);
    }

    public void refund(Long userProgramId){ // 13번 id
        UserProgram userProgram = userProgramRepository.findById(userProgramId).orElseThrow();
        int amount = userProgram.getAmount(); // 프로그램 결제 금액
        int maxCount = userProgram.getMaxCount();
        int count = userProgram.getCount();
        // 회차당 1회 금액
        int result = amount/maxCount;
        // 진행 횟수에 따른 환불 금액
        result = result * count;
        userProgram.setAmount(result);
        userProgram.setState(UserProgram.UserProgramState.FINISH);
        userProgram.setEndTime(LocalDateTime.now());
        userProgramRepository.save(userProgram);
        UserProgramDto.fromEntity(userProgram);
    }


    public UserProgram findByUserIdAndProgramId(Long userId, Long programId){
        return userProgramRepository.findByUserIdAndProgramId(userId, programId);
    }
    //      programId에 해당하는 모든 user_program의 id가져오기
    public List<UserProgram> findAllByProgramIdConvertId(Long programId){
        return userProgramRepository.findAllByProgramId(programId);
    }



}
