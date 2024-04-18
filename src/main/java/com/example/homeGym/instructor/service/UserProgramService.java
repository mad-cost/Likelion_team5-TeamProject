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

    public List<Long> findAllByUserIdConvertProgramId(Long userId){
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

    public void deleteByProgram(List<Long> userPrograms, Long programId){ //(1, 3) / (2)
        List<UserProgram> userIds = userProgramRepository.findAllById(userPrograms);
        for (UserProgram userId : userIds){
            if (userId.getProgramId().equals(programId)){
                userProgramRepository.delete(userId);
                break;
            }
        }
    }


}
