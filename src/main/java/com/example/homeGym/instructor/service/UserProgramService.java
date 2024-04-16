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
}
