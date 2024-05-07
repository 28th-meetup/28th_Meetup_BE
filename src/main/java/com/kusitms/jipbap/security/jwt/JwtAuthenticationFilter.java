package com.kusitms.jipbap.security.jwt;

import com.kusitms.jipbap.security.jwt.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        // Authorization 헤더가 없을경우 익명 사용자로 접근 가능
        if (bearerToken!=null) {
            String jwtToken = jwtTokenProvider.resolveToken(bearerToken);
            if (jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new InvalidTokenException("토큰이 유효하지 않습니다.");
            }
        }
        filterChain.doFilter(request, response);
    }
}