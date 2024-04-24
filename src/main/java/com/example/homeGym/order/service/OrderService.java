package com.example.homeGym.order.service;


import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.order.dto.ProgramOrderDto;
import com.example.homeGym.order.entity.ProgramOrder;
import com.example.homeGym.order.repo.OrderRepository;
import com.example.homeGym.toss.dto.PaymentConfirmDto;
import com.example.homeGym.toss.service.TossHttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final TossHttpService tossService;
    private final OrderRepository orderRepository;
    private final ProgramRepository programRepository;


    public Object confirmPayment(PaymentConfirmDto dto) {

        Object tossPaymentObj = tossService.confirmPayment(dto);

        String programTitle = ((LinkedHashMap<?, ?>) tossPaymentObj)
                .get("programTitle").toString();

        Long programId = Long.parseLong(programTitle.split("-")[0]);
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        return ProgramOrderDto.fromEntity(orderRepository.save(ProgramOrder.builder()
                .program(program)
                .tossPaymentKey(dto.getPaymentKey())
                .tossOrderId(dto.getOrderId())
                .build()));

    }


}
