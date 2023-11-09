package com.kusitms.jipbap.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //success
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공했습니다."),

    //internal
    INTERNAL_SERVER_ERROR(false,HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버 내부에서 문제가 발생했습니다."),
    NOT_FOUND(false, HttpStatus.NOT_FOUND.value(), "해당 로그인 정보는 존재하지 않습니다."),
    UNAUTHORIZED(false, HttpStatus.UNAUTHORIZED.value(), "권한이 없습니다."),

    //auth
    EMAIL_EXISTS_ERROR(false, HttpStatus.BAD_REQUEST.value(), "이미 존재하는 이메일입니다."),
    INVALID_EMAIL_ERROR(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 이메일 정보입니다."),
    INVALID_PASSWORD_ERROR(false, HttpStatus.BAD_REQUEST.value(), "비밀번호를 확인해주세요. 카카오 계정이라면 카카오 로그인으로 시도해주세요."),
    INVALID_ACCESS_TOKEN_ERROR(false, HttpStatus.BAD_REQUEST.value(), "AccessToken 정보를 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN_ERROR(false, HttpStatus.BAD_REQUEST.value(), "RefreshToken 정보를 찾을 수 없습니다."),

    //user
    USER_NOT_FOUND_ERROR(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 유저입니다."),

    //chat
    ROOM_NOT_FOUND_ERROR(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 채팅방입니다."),

    //store
    STORE_NOT_FOUND_ERROR(false, HttpStatus.BAD_REQUEST.value(), "존재하지 않는 가게입니다."),
    STORE_ALREADY_EXISTS_ERROR(false, HttpStatus.BAD_REQUEST.value(), "이미 채팅방을 만든 유저입니다.")
    ;

    private Boolean isSuccess;
    private int code;
    private String message;

    ErrorCode(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
