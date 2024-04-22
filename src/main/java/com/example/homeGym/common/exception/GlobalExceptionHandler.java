package com.example.homeGym.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GlobalExceptionHandler extends RuntimeException{
    private CustomGlobalErrorCode customGlobalErrorCode;
    @Override
    public String getMessage() {
        return customGlobalErrorCode.getMessage(); // 메시지 반환
    }
}
