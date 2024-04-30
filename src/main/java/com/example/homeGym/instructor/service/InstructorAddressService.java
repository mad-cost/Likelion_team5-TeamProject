package com.example.homeGym.instructor.service;

import com.example.homeGym.instructor.entity.InstructorAddress;
import com.example.homeGym.instructor.repository.InstructorAddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstructorAddressService {

    private final InstructorAddressRepository instructorAddressRepository;

    public void saveInstructorAddress(String siDo, String siGunGu, String dong, Long instructorId){
        InstructorAddress instructorAddress = new InstructorAddress(null, siDo, siGunGu, dong, instructorId );
        instructorAddressRepository.save(instructorAddress);
    }

    public List<InstructorAddress> getInstructorAddresses(Long instructorId) {
        return instructorAddressRepository.findByInstructorId(instructorId);
    }

    public void deleteInstructorAddress(Long addressId) {
        instructorAddressRepository.deleteById(addressId);
    }
}
