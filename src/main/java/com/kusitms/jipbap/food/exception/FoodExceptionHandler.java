package com.kusitms.jipbap.food.exception;

import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.food.exception.CategoryNotExistsException;
import com.kusitms.jipbap.food.exception.FoodNotExistsException;
import com.kusitms.jipbap.food.exception.FoodOptionNotExistsException;
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
public class FoodExceptionHandler {
    @ExceptionHandler(CategoryNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handleCategoryNotExistsException(CategoryNotExistsException e, HttpServletRequest request) {
        log.warn("FOOD-001> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.CATEGORY_NOT_FOUND_ERROR);
    }

    @ExceptionHandler(FoodNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handelFoodNotExistsException(FoodNotExistsException e, HttpServletRequest request) {
        log.warn("FOOD-002> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.FOOD_NOT_FOUND_ERROR);
    }


    @ExceptionHandler(FoodOptionNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse<?> handelFoodOptionNotExistsException(FoodOptionNotExistsException e, HttpServletRequest request) {
        log.warn("FOOD-003> 요청 URI: " + request.getRequestURI() + ", 에러 메세지: " + e.getMessage());
        return new CommonResponse<>(ErrorCode.FOOD_OPTION_NOT_FOUND_ERROR);
    }

}
