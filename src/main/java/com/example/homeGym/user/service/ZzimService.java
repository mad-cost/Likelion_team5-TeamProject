package com.example.homeGym.user.service;

import com.example.homeGym.user.dto.ZzimDto;
import com.example.homeGym.user.entity.Zzim;
import com.example.homeGym.user.repository.ZzimRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZzimService {
    private final ZzimRepository zzimRepository;

    public List<ZzimDto> findByUserId(Long userId){
        List<ZzimDto> zzimDtoList = new ArrayList<>();
        List<Zzim> zzimList = zzimRepository.findByUserId(userId);
        for (Zzim zzim :
                zzimList) {
            zzimDtoList.add(ZzimDto.fromEntity(zzim));
        }

        return zzimDtoList;
    }
}
