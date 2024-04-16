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
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String week;
    @Setter
    private String time;

//    @OneToMany
//    private Instructor instructor;
    @Setter
    private Long instructorId;
    @CreationTimestamp
    private LocalDateTime createAt;
}
