package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.entity.Address;
import com.example.homeGym.instructor.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<String> getDistinctSiDo() {
        return addressRepository.findDistinctSiDo();
    }

    public List<String> getSiGunGu(String siDo) {
        return addressRepository.findBySiDoOrderBySiGunGuAsc(siDo)
                .stream()
                .map(Address::getSiGunGu)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getDong(String siGunGu) {
        return addressRepository.findBySiGunGuOrderByDongAsc(siGunGu)
                .stream()
                .map(Address::getDong)
                .distinct()
                .collect(Collectors.toList());
    }

}
