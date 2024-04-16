package com.example.homeGym.toss.repo;

import com.example.homeGym.toss.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 주문 Id 찾기
    Optional<Payment> findByOrderId(String orderId);

    // 유저별 주문 찾기
    Optional<Payment> findByPaymentKeyAndUserId(String PaymentKey, Long userId);



}
