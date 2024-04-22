package com.example.homeGym.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomGlobalErrorCode {
    // 게시판 관련 에러
    USER_NOT_EXISTS(404, "4001", "존재하지 않는 게시글입니다."),
    USER_FORBIDDEN(403, "4002", "게시글의 소유 권한이 없습니다."),
    COMMENT_NOT_EXISTS(404, "4003", "존재하지 않는 댓글입니다."),
    COMMENT_MISMATCH(401, "4004", "요청한 댓글이 해당 게시글의 댓글이 아닙니다."),
    COMMENT_FORBIDDEN(403, "4005", "댓글의 소유 권한이 없습니다."),
    AUTHENTICATION_FAILED(403, "8001", "인증 확인에 실패했습니다."),

    // 스켸줄 관련 에러
    SCHEDULE_NOT_EXISTS(404,"4003", "존재하지 않는 스케쥴입니다."),

    // 프로그램 괸련 에러
    PROGRAM_NOT_EXISTS(404,"4003", "존재하지 않는 프로그램입니다.");

    private int status;
    private String code;
    private String message;

//    CustomGlobalErrorCode(int i, String number, String s) {
//    }
}
