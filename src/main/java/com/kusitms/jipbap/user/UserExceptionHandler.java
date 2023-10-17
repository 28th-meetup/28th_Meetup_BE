package com.kusitms.jipbap.user;

import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.user.exception.UserNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> userNotFoundException(UserNotFoundException e, HttpServletRequest request) {
        log.warn("User-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.USER_NOT_FOUND_ERROR);
    }
}
