package com.kusitms.jipbap.user.controller;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.user.exception.*;
import jakarta.persistence.ElementCollection;
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

    @ExceptionHandler(GeocodingConnectionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> GeocodingConnectionException(GeocodingConnectionException e, HttpServletRequest request) {
        log.warn("UserAddress-003> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.GEOCODING_CONNECTION_ERROR);
    }

    @ExceptionHandler(GeocodingUnknownAddressException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> GeocodingUnknownAddressException(GeocodingUnknownAddressException e, HttpServletRequest request) {
        log.warn("UserAddress-004> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.GEOCODING_UNKNOWN_ADDRESS_ERROR);
    }

    @ExceptionHandler(GeocodingInvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> GeocodingInvalidRequestException(GeocodingInvalidRequestException e, HttpServletRequest request) {
        log.warn("UserAddress-005> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.GEOCODING_INVALID_REQUEST_ERROR);
    }

    @ExceptionHandler(GeocodingQueryMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> GeocodingQueryMissingException(GeocodingQueryMissingException e, HttpServletRequest request) {
        log.warn("UserAddress-006> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.GEOCODING_QUERY_MISSING_ERROR);
    }

    @ExceptionHandler(PostalCodeNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> PostalCodeNotFoundError(PostalCodeNotFoundException e, HttpServletRequest request) {
        log.warn("UserAddress-007> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.POSTAL_CODE_NOT_FOUND_ERROR);
    }
}
