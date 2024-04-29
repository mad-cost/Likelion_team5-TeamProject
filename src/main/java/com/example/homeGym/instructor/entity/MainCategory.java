package com.example.homeGym.instructor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MainCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "mainCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubCategory> subCategories;
}
