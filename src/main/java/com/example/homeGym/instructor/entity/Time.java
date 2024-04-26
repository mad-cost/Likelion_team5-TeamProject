package com.example.homeGym.instructor.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum Time {

    SIX_TO_NINE("6시~9시", 6, 9),
    NINE_TO_TWELVE("9시~12시", 9, 12),
    TWELVE_TO_FIFTEEN("12시~15시", 12, 15),
    FIFTEEN_TO_EIGHTEEN("15시~18시", 15, 18),
    EIGHTEEN_TO_TWENTY_ONE("18시~21시", 18, 21),
    TWENTY_ONE_TO_TWENTY_FOUR("21시~24시", 21, 24);

    private final String description;
    private final int startHour;
    private final int endHour;

    Time(String description, int startHour, int endHour) {
        this.description = description;
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
