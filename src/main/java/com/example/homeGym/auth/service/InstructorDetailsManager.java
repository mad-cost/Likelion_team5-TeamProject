package com.example.homeGym.auth.service;

import com.example.homeGym.auth.dto.CustomInstructorDetails;
import com.example.homeGym.auth.dto.CustomUserDetails;
import com.example.homeGym.instructor.entity.Instructor;
import com.example.homeGym.instructor.repository.InstructorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InstructorDetailsManager implements UserDetailsManager {

    private final InstructorRepository instructorRepository;

    @Override
    public void createUser(UserDetails user) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void deleteUser(String username) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED);
    }

    @Override
    public boolean userExists(String email) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Instructor> optionalInstructor = instructorRepository.findByEmail(email);
        if (optionalInstructor.isEmpty())
            throw new UsernameNotFoundException(email);

        Instructor instructor = optionalInstructor.get();

        return CustomInstructorDetails.builder()
                .instructor(instructor)
                .name(instructor.getName())
                .password(instructor.getPassword())
                .gender(String.valueOf(instructor.getGender()))
                .birthyear(instructor.getBirthyear())
                .birthday(instructor.getBirthday())
                .roles(instructor.getRoles())
                .state(String.valueOf(instructor.getState()))
                .career(instructor.getCareer())
                .profileImageUrl(instructor.getProfileImageUrl())
                .certificate(instructor.getCertificate())
                .medal(instructor.getMedal())
                .email(instructor.getEmail())
                .phone(instructor.getPhone())
                .bank(instructor.getBank())
                .bankName(instructor.getBankName())
                .rating(instructor.getRating())
                .build();
    }
}
