package com.example.homeGym.common.util;

import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationUtilService {
    private final UserService userService;
    public Long getId(Authentication authentication){
        UserDto userDto = userService.findByAuthentication(authentication);
        return userDto.getId();
    }
}
