package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.repository.UserProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProgramService {
    private final UserProgramRepository userProgramRepository;
}
