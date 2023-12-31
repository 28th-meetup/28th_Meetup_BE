package com.kusitms.jipbap.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kusitms.jipbap.common.response.ErrorCode;
import com.kusitms.jipbap.common.response.CommonResponse;
import com.kusitms.jipbap.security.jwt.exception.EmptyTokenException;
import com.kusitms.jipbap.security.jwt.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ExceptionHandleFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException exception) {
            setErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_ACCESS_TOKEN_ERROR,
                    request, response, exception, "TOKEN-ERROR-01"
            );

        } catch (EmptyTokenException exception) {
            setErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.INVALID_ACCESS_TOKEN_ERROR,
                    request, response, exception, "TOKEN-ERROR-02"
            );
        } catch (Exception exception) {
            setErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.INTERNAL_SERVER_ERROR,
                    request, response, exception, "DEFAULT-ERROR-01"
            );
        }
    }

    private void setErrorResponse(HttpStatus status,
                                  ErrorCode code,
                                  HttpServletRequest request,
                                  HttpServletResponse response,
                                  Exception exception,
                                  String errorCode) {
        response.setStatus(status.value());
        response.setContentType("application/json");
        CommonResponse<?> commonResponse = new CommonResponse<>(code, exception.getMessage());
        try {
            log.error("에러코드 {}: {} [에러 종류: {}, 요청 url: {}]",
                    errorCode,
                    exception.getMessage(),
                    exception.getClass(),
                    request.getRequestURI());
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
