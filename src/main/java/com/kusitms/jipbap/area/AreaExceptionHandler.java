package com.kusitms.jipbap.area;

import com.kusitms.jipbap.area.exception.DeviceIdExistsException;
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
public class AreaExceptionHandler {

    @ExceptionHandler(DeviceIdExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleDeviceIdExistsException(DeviceIdExistsException e, HttpServletRequest request) {
        log.warn("Area-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.DEVICE_ID_EXISTS_ERROR);
    }
}
