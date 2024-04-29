package com.example.homeGym.instructor.controller;

import com.example.homeGym.instructor.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("instructor/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/siDo")
    public ResponseEntity<List<String>> getSiDo() {
        List<String> sidoList = addressService.getDistinctSiDo();
        return ResponseEntity.ok(sidoList);
    }

    @GetMapping("/siGunGu")
    public ResponseEntity<List<String>> getSiGunGu(@RequestParam String siDo) {
        List<String> sigunguList = addressService.getSiGunGu(siDo);
        return ResponseEntity.ok(sigunguList);
    }

    @GetMapping("/dong")
    public ResponseEntity<List<String>> getDong(@RequestParam String siGunGu) {
        List<String> dongList = addressService.getDong(siGunGu);
        return ResponseEntity.ok(dongList);
    }
}
