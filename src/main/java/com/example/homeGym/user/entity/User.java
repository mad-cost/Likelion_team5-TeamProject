package com.example.homeGym.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@ToString
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(unique = true)
    private String email;

    @Setter
    private String name;

    @Setter
    private String password;

    @Setter
    private String profileImageUrl;

    @Setter
    private String gender;

    @Setter
    private String birthday;

    @Setter
    private String birthyear;

    @Builder.Default
    private String roles = "ROLE_USER";

    @Enumerated(EnumType.STRING)
    private UserState state;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public enum UserState {
        USER, WITHDRAWAL
    }
}