package com.example.homeGym.user.service;

import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
  
    public List<UserDto> findAllByOrderByName(){
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : userRepository.findAllByOrderByName()){
            userDtos.add(UserDto.fromEntity(user));
        }
        return userDtos;
    }


    public UserDto findById(Long userId){
        return UserDto.fromEntity(userRepository.findById(userId).orElseThrow());
    }
}
