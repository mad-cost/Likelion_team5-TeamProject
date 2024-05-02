package com.example.homeGym.user.service;

import com.example.homeGym.user.entity.Apply;
import com.example.homeGym.user.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private ApplyRepository applyRepository;

    public void saveApply(Apply apply) {
        applyRepository.save(apply);
    }

    public void updateApply(Apply apply) {
        applyRepository.save(apply);
    }

    public Apply getApplyById(Long id) {
        return applyRepository.findById(id).orElse(null);
    }
}
