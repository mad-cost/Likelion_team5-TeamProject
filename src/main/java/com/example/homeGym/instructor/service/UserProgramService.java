package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.dto.UserProgramDto;
import com.example.homeGym.instructor.entity.UserProgram;
import com.example.homeGym.instructor.repository.UserProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public List<Long> findAllByUserId(Long userId){
        List<Long> result = new ArrayList<>();
//        userProgram에서 userId 가져오기
        for (UserProgram userProgram : userProgramRepository.findAllByUserId(userId)){
//            userProgram의 state가 IN_PROGRESS인 속성만 필요
            if (userProgram.getState().equals(UserProgram.UserProgramState.IN_PROGRESS)){
//                가져온 userId의 programId를 result에 담아주기
            result.add(userProgram.getProgramId());
            } else continue;
        }
        return result;
    }

    public List<UserProgramDto> findAllByUserIdDto(Long userId){
        List<UserProgramDto> userProgramDtos = new ArrayList<>();

        for (UserProgram userProgram : userProgramRepository.findAllByUserId(userId)){
            if (userProgram.getState().equals(UserProgram.UserProgramState.IN_PROGRESS)){
                userProgramDtos.add(UserProgramDto.fromEntity(userProgram));
            }else continue;
        }
        return userProgramDtos;
    }


}
