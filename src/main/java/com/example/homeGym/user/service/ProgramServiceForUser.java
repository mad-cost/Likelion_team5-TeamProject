package com.example.homeGym.user.service;

import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.repository.ProgramRepositoryForUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProgramServiceForUser {
    private final ProgramRepositoryForUser programRepositoryForUser;

    public Program findById(Long programId){
        return programRepositoryForUser.findById(programId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
