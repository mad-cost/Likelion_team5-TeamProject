package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserProgram {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    @Enumerated(EnumType.STRING)
    private UserProgramState state;
    @Setter
    private Integer count;
    @Setter
    private Integer maxCount;
    @Setter
    private Integer amount;
    @CreationTimestamp
    private LocalDateTime createAt;
    @Setter
    private LocalDateTime endTime;

//    @ManyToOne
//    private Program program;
    @Setter
    private Long programId;

//    @ManyToOne
//    private User user;
    @Setter
    private Long userId;

    public enum UserProgramState{
        PAYMENT_COMPLETED, IN_PROGRESS, FINISH, CANCEL
    }
}