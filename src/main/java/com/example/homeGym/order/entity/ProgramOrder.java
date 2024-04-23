package com.example.homeGym.order.entity;


import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProgramOrder {

    private Long orderId;

    private User user;
    private Program program;

    private Long amount;

    private String tossPaymentKey;
    private String tossOrderId;


}
