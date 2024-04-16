package com.example.homeGym.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    private String profileImageUrl;

    @Setter
    private String gender;

    @Setter
    private String email;

    @Setter
    private String birthday;

    @Builder.Default
    private String roles = "ROLE_USER";

    @Setter
    @Enumerated(EnumType.STRING)
    private UserState state;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum UserState {
        USER, WITHDRAWAL
    }
}