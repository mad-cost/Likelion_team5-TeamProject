package com.example.homeGym.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private Long userProgramId;

    @Setter
    private Long userId;

    @Setter
    private Integer stars;

    @Setter
    @ElementCollection
    private List<String> imageUrl;

    @Setter
    private String memo;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @Setter
    private String dateCreatedAt; // yyyy년MM월dd일
}