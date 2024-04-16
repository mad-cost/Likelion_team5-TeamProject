package com.example.homeGym.toss.entity;

import lombok.Getter;

@Getter
public enum PayType {
    CARD("카드"),
    CASH("현금"),
    POINT("포인트");

    private final String PayTypeDescription;

    PayType(String PayTypeDescription) {
        this.PayTypeDescription = PayTypeDescription;
    }

}