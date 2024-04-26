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
    PROGRAM_NOT_EXISTS(404,"4003", "존재하지 않는 프로그램입니다."),
    PROGRAM_FORBIDDEN(403, "4005", "프로그램 소유 권한이 없습니다."),

    // 정산 관련 에러
    SETTLEMENT_EXCEEDS_AVAILABLE(400, "9001", "정산 신청 금액이 가능 금액을 초과합니다."),
    SETTLEMENT_NOT_FOUND(404, "9002", "정산 정보를 찾을 수 없습니다."),
    SETTLEMENT_NOT_ELIGIBLE(403, "9003", "정산 자격이 충족되지 않았습니다."),
    SETTLEMENT_INVALID(404, "9002","정산금액이 유효하지 않습니다."),

    // 유저 프로그램 관련 에러
    USER_PROGRAM_EXISTS(404,"4003", "유저가 존재하는 프로그램입니다.");

    private int status;
    private String code;
    private String message;

//    CustomGlobalErrorCode(int i, String number, String s) {
//    }
}
