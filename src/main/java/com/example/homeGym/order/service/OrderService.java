//package com.example.homeGym.order.service;
//
//
//import com.example.homeGym.instructor.entity.Program;
//import com.example.homeGym.order.dto.ProgramOrderDto;
//import com.example.homeGym.order.entity.ProgramOrder;
//import com.example.homeGym.order.repo.OrderRepository;
//import com.example.homeGym.toss.dto.PaymentConfirmDto;
//import com.example.homeGym.toss.service.TossHttpService;
//import com.example.homeGym.user.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.LinkedHashMap;
//
//
//@Service
//@RequiredArgsConstructor
//public class OrderService {
//    private final TossHttpService tossService;
//    private final OrderRepository orderRepository;
//
//
//    public Object confirmPayment(PaymentConfirmDto dto) {
//
//        Object tossPayment = tossService.responsePayment(dto);
//
//        String programTitle = ((LinkedHashMap<? ,?>) tossPayment).get("programTitle").toString();
//        Object price = ((LinkedHashMap<? ,?>) tossPayment).get("price");
//
//        Long programId = Long.parseLong(programTitle.split("-")[0]);
//
//
//        return ProgramOrderDto.fromEntity(orderRepository.save(ProgramOrder.builder()
//                .build()));
//
//    }
//
//
//
//}
