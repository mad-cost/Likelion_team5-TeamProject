package com.example.homeGym.admin.service;

import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class NumberUtils {
  public String addCommasToNumber(int number) {
    DecimalFormat formatter = new DecimalFormat("#,###");
    return formatter.format(number);
  }
}

