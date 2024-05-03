package com.example.homeGym.user.service;

import com.example.homeGym.user.dto.ZzimDto;
import com.example.homeGym.user.entity.Zzim;
import com.example.homeGym.user.repository.ZzimRepository;
import jakarta.transaction.Transactional;
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
    private final ProgramServiceForUser programServiceForUser;

//    public List<ZzimDto> findByUserId(Long userId){
//        List<ZzimDto> zzimDtoList = new ArrayList<>();
//        List<Zzim> zzimList = zzimRepository.findByUserId(userId);
//        for (Zzim zzim :
//                zzimList) {
//            zzimDtoList.add(ZzimDto.fromEntity(zzim));
//        }
//
//        return zzimDtoList;
//    }

    public List<ZzimDto> findZziom(Long userId){
        List<ZzimDto> zzimDtoList = new ArrayList<>();
        List<Zzim> zzimList = zzimRepository.findByUserId(userId);
        for (Zzim zzim :
                zzimList) {
            zzimDtoList.add(ZzimDto.fromEntity(zzim));
        }
        //프로그램 정보 가져오기
        for (ZzimDto dto :
                zzimDtoList) {
            dto.setProgram(programServiceForUser.findById(dto.getProgramId()));
        }

        return zzimDtoList;

    }
    @Transactional
    public void deleteZzim(long zzimId){
        zzimRepository.deleteById(zzimId);
    }
}
