package com.kusitms.jipbap.user;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.user.exception.RegionNotFoundException;
import com.kusitms.jipbap.user.exception.RegionExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class UserAddressExceptionController {
    @ExceptionHandler(RegionExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> RegionExistsException(RegionExistsException e, HttpServletRequest request) {
        log.warn("UserAddress-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.REGION_ALREADY_EXISTS_ERROR);
    }

    @ExceptionHandler(RegionNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> RegionNotFoundException(RegionNotFoundException e, HttpServletRequest request) {
        log.warn("UserAddress-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.REGION_NOT_FOUND_ERROR);
    }
}
