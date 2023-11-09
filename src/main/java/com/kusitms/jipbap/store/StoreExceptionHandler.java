package com.kusitms.jipbap.store;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.store.exception.StoreExistsException;
import com.kusitms.jipbap.store.exception.StoreNotExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class StoreExceptionHandler {
    @ExceptionHandler(StoreNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleStoreNotExistsException(StoreNotExistsException e, HttpServletRequest request) {
        log.warn("STORE-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.STORE_NOT_FOUND_ERROR);
    }

    @ExceptionHandler(StoreExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleStoreExistsException(StoreExistsException e, HttpServletRequest request) {
        log.warn("STORE-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.STORE_ALREADY_EXISTS_ERROR);
    }
}
