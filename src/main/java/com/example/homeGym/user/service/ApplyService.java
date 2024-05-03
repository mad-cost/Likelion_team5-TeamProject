package com.example.homeGym.user.service;

import com.example.homeGym.instructor.entity.Time;
import com.example.homeGym.instructor.entity.Week;
import com.example.homeGym.user.entity.Apply;
import com.example.homeGym.user.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;

    public void saveApply(String week, String time, String count, Long userId, Long programId) {
        // 선택한 요일, 시간, 회차권을 이용하여 Apply 엔티티를 생성하고 저장합니다.
        Apply apply = new Apply();
        apply.setAble_week(Week.valueOf(week));
        apply.setAble_time(Time.valueOf(time));
        apply.setCount(Integer.parseInt(count));
        apply.setProgramId(programId);
        apply.setUserId(userId);
        apply.setApplyState(Apply.ApplyState.PAID);
        applyRepository.save(apply);
    }

    public void updateApply(Apply apply) {
        applyRepository.save(apply);
    }

    public Apply getApplyById(Long id) {
        return applyRepository.findById(id).orElse(null);
    }
}
