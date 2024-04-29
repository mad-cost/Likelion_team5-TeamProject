package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@SuperBuilder
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reviewId;
    @Setter
    private String content;
    @CreationTimestamp
    private LocalDateTime createAt;
    @Setter
    @ManyToOne
    private Instructor instructor;
    @Setter
    private String dateCreatedAt;
}

