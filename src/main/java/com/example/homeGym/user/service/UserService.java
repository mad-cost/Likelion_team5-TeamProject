package com.example.homeGym.user.service;

import com.example.homeGym.admin.dto.AdminDto;
import com.example.homeGym.auth.dto.CustomInstructorDetails;
import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.auth.jwt.JwtTokenUtils;
import com.example.homeGym.auth.service.JpaUserDetailsManager;
import com.example.homeGym.auth.utils.CookieUtil;
import com.example.homeGym.auth.utils.PasswordEncodeConfig;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final JpaUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtils;
    private final CookieUtil cookieUtil;

    public boolean signinAdmin(HttpServletResponse res, String email, String password){

        Optional<User> nowUser= userRepository.findByEmail(email);
        User user = nowUser.get();
        Boolean pwCheck = passwordEncoder.matches(password, user.getPassword());
        if(pwCheck){
            CustomUserDetails customInstructorDetails = (CustomUserDetails) userDetailsManager.loadUserByUsername(email);
            String jwtToken = jwtTokenUtils.generateToken(customInstructorDetails);

            cookieUtil.createCookie(res,"Authorization", jwtToken);
            return true;
        }
        return false;

    }

//    public void saveAdmin(AdminDto adminDto){
//        if (!userRepository.existsByEmail(adminDto.getEmail())){
//            userDetailsManager.createUser(CustomUserDetails.builder()
//                    .name(adminDto.getName())
//                    .email(adminDto.getEmail())
//                    .password(passwordEncoder.encode(adminDto.getPassword()))
//                    .profileImageUrl(adminDto.getProfileImageUrl())
//                    .gender(String.valueOf(adminDto.getGender()))
//                    .birthyear(adminDto.getBirthyear())
//                    .birthday(adminDto.getBirthday())
//                    .roles("ROLE_ADMIN")
//                    .build());
//        }
//    }

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

    public User findByLongId(Long userId){
        return userRepository.findById(userId).orElseThrow();
    }


}
