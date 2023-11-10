package com.kusitms.jipbap.auth;

import com.kusitms.jipbap.auth.exception.*;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.common.response.CommonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(EmailExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleEmailExistsException(EmailExistsException e, HttpServletRequest request) {
        log.warn("Auth-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.EMAIL_EXISTS_ERROR);
    }

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleInvalidEmailException(InvalidEmailException e, HttpServletRequest request) {
        log.warn("Auth-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.INVALID_EMAIL_ERROR);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleInvalidPasswordException(InvalidPasswordException e, HttpServletRequest request) {
        log.warn("Auth-003> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.INVALID_PASSWORD_ERROR);
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException e, HttpServletRequest request) {
        log.warn("Auth-004> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.INVALID_REFRESH_TOKEN_ERROR);
    }

    @ExceptionHandler(JsonException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleJsonException(JsonException e, HttpServletRequest request) {
        log.warn("Auth-005> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UsernameExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleUsernameExistsException(UsernameExistsException e, HttpServletRequest request) {
        log.warn("Auth-006> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.EMAIL_EXISTS_ERROR);
    }
}
