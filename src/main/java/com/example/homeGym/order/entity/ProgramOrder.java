package com.example.homeGym.order.entity;


import com.example.homeGym.instructor.entity.Program;
import com.example.homeGym.user.entity.User;
import jakarta.persistence.*;
import lombok.*;



@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ProgramOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Program program;

    private Long amount;

    private String tossPaymentKey;
    private String tossOrderId;


}
