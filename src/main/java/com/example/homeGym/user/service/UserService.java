package com.example.homeGym.user.service;

import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public UserDto findByAuthentication(Authentication authentication){
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return UserDto.fromEntity(userRepository.findByEmail(customUserDetails.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }



}
