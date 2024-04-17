package com.example.homeGym.common.util;

import com.example.homeGym.CustomInstructorDetails;
import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// 현재 접속자
@Component
@RequiredArgsConstructor
public class AuthenticationFacade {
    public Instructor getCurrentInstructor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof CustomInstructorDetails instructorDetails) {
            return instructorDetails.getInstructor();
        } else {
            throw new GlobalExceptionHandler(CustomGlobalErrorCode.AUTHENTICATION_FAILED);
        }
    }
}
