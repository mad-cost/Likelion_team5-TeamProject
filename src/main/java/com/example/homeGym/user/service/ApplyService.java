package com.example.homeGym.user.service;

import com.example.homeGym.user.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;
}
