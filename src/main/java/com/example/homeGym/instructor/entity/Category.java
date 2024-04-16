package com.example.homeGym.instructor.entity;

import lombok.Getter;
import java.util.List;

@Getter
public enum Category {
    YOGA(List.of("Mind Health Management")),
    PRENATAL_POSTNATAL(List.of("Prenatal/Postnatal Care")),
    EXERCISE(List.of("Diet", "Strength", "Pain Management"));

    private final List<String> subCategories;
    Category(List<String> subCategories) {
        this.subCategories = subCategories;
    }
}
