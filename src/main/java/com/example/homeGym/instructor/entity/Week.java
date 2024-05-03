package com.example.homeGym.instructor.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;

@Getter
public enum Week {
    MONDAY("Monday", "월요일", 1),
    TUESDAY("Tuesday", "화요일", 2),
    WEDNESDAY("Wednesday", "수요일", 3),
    THURSDAY("Thursday", "목요일", 4),
    FRIDAY("Friday", "금요일", 5),
    SATURDAY("Saturday", "토요일", 6),
    SUNDAY("Sunday", "일요일", 7);

    private final String englishName;
    private final String koreanName;
    private final int order;

    Week(String englishName, String koreanName, int order) {
        this.englishName = englishName;
        this.koreanName = koreanName;
        this.order = order;
    }

    public static Week[] getOrderedValues() {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(Week::getOrder))
                .toArray(Week[]::new);
    }
}