package com.kusitms.jipbap.event.exception;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class EventExceptionHandler {
    @ExceptionHandler(EventNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleEventNotExistsException(EventNotExistsException e, HttpServletRequest request) {
        log.warn("Event-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.EVENT_NOT_EXISTS_ERROR);
    }

    @ExceptionHandler(EventExhaustException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleEventExhaustException(EventExhaustException e, HttpServletRequest request) {
        log.warn("Event-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.EVENT_EXHAUST_ERROR);
    }

    @ExceptionHandler(AlreadyExistsEventUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleEventExhaustException(AlreadyExistsEventUserException e, HttpServletRequest request) {
        log.warn("Event-003> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ALREADY_EXISTS_USER_EVENT_ERROR);
    }
}
