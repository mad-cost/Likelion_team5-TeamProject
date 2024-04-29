package com.example.homeGym.order.service;


import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.instructor.repository.ProgramRepository;
import com.example.homeGym.order.dto.ProgramOrderDto;
import com.example.homeGym.order.entity.ProgramOrder;
import com.example.homeGym.order.repo.OrderRepository;
import com.example.homeGym.toss.dto.PaymentCancelDto;
import com.example.homeGym.toss.dto.PaymentConfirmDto;
import com.example.homeGym.toss.entity.Payment;
import com.example.homeGym.toss.repo.PaymentRepository;
import com.example.homeGym.toss.service.TossHttpService;
import com.example.homeGym.user.dto.UserDto;
import com.example.homeGym.user.entity.User;
import com.example.homeGym.user.repository.UserRepository;
import com.example.homeGym.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final TossHttpService tossService;
    private final OrderRepository orderRepository;
    private final ProgramRepository programRepository;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;
    private final UserService userService;


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

    public Payment requestTossPayment(Payment payment, Long id) {
        UserDto user = userService.findById(id);

        payment.setUserId(user.getId());

        return paymentRepository.save(payment);
    }

    public List<ProgramOrderDto> readAll() {
        return orderRepository.findAll().stream()
                .map(ProgramOrderDto::fromEntity)
                .toList();
    }

    public ProgramOrderDto readOne(Long id) {
        return orderRepository.findById(id)
                .map(ProgramOrderDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Object readTossPayment(Long id) {
        ProgramOrder order = orderRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        Object response = tossService.getPayment(order.getTossPaymentKey());

        return response;
    }

    public Object cancelPayment(
            Long id,
            PaymentCancelDto dto
    ) {
        ProgramOrder order = orderRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                );

        return tossService.cancelPayment(order.getTossPaymentKey(), dto);
    }


}
