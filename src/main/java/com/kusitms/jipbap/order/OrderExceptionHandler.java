package com.kusitms.jipbap.order;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.order.exception.OrderNotExistsException;
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
        return new CommonResponse<>(ErrorCode.STORE_NOT_FOUND_ERROR);
    }
}
