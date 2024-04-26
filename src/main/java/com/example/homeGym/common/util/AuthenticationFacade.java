package com.example.homeGym.common.util;

import com.example.homeGym.auth.dto.CustomInstructorDetails;
import com.example.homeGym.common.exception.CustomGlobalErrorCode;
import com.example.homeGym.common.exception.GlobalExceptionHandler;
import com.example.homeGym.instructor.entity.Instructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

// 현재 접속자
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthenticationFacade {
    public Instructor getCurrentInstructor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Authentication object: {}", auth);

        if (auth != null && auth.getPrincipal() instanceof CustomInstructorDetails) {
            CustomInstructorDetails instructorDetails = (CustomInstructorDetails) auth.getPrincipal();
            Instructor instructor = instructorDetails.getInstructor();
            if (instructor == null) {
                log.error("Instructor object is null within CustomInstructorDetails");
                throw new RuntimeException("Instructor information is not available in the current session.");
            }
            log.info("InstructorDetails found with name: {}", instructorDetails.getUsername());
            return instructor;
        } else {
            log.error("Authentication object did not contain expected principal type or is null.");
            throw new RuntimeException("Authentication failed or wrong principal type.");
        }
    }
}
