package com.example.homeGym.instructor.entity;

import lombok.Getter;

@Getter
public enum Week {
    MONDAY("Monday", "월요일"),
    TUESDAY("Tuesday", "화요일"),
    WEDNESDAY("Wednesday", "수요일"),
    THURSDAY("Thursday", "목요일"),
    FRIDAY("Friday", "금요일"),
    SATURDAY("Saturday", "토요일"),
    SUNDAY("Sunday", "일요일");

    private final String englishName;
    private final String koreanName;

    private Week(String englishName, String koreanName) {
        this.englishName = englishName;
        this.koreanName = koreanName;
    }
}
