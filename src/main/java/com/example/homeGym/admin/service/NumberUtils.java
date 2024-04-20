package com.example.homeGym.admin.service;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

// 금액 표기하기
@Component
public class NumberUtils {
  public String addCommasToNumber(int number) {
    DecimalFormat formatter = new DecimalFormat("#,###");
    return formatter.format(number);
  }
}

