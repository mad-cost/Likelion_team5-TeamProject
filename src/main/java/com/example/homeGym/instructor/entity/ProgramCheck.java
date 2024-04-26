package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramCheck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private Long userProgramId;
    @Setter
    private String memo;
    @Setter
    private LocalDate programDate;
    @Setter
    @Enumerated(EnumType.STRING)
    private Time time;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
