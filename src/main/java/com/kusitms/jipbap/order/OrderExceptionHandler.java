package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.order.exception.*;
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
public class OrderExceptionHandler {
    @ExceptionHandler(OrderNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleOrderNotExistsException(OrderNotExistsException e, HttpServletRequest request) {
        log.warn("ORDER-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ORDER_NOT_EXISTS_ERROR);
    }

    @ExceptionHandler(OrderStatusFromStringError.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleOrderStatusFromStringError(OrderStatusFromStringError e, HttpServletRequest request) {
        log.warn("ORDER-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ORDER_STATUS_FROM_STRING_ERROR);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleOrderNotFoundException(OrderNotFoundException e, HttpServletRequest request) {
        log.warn("ORDER-003> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ORDER_NOT_FOUND_ERROR);
    }

    @ExceptionHandler(OrderNotExistsByOrderStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleOrderNotExistsByOrderStatusException(OrderNotExistsByOrderStatusException e, HttpServletRequest request) {
        log.warn("ORDER-004> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.ORDER_NOT_EXISTS_BY_ORDER_STATUS_ERROR);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleUnauthorizedAccessException(UnauthorizedAccessException e, HttpServletRequest request) {
        log.warn("ORDER-005> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.UNAUTHORIZED_ACCESS_ERROR);
    }

}
