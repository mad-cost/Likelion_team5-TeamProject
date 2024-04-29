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

    @Setter
    private Long programId;

    @Setter
    private Long userId;

    public enum UserProgramState{
        IN_PROGRESS, FINISH, CANCEL
    }
}