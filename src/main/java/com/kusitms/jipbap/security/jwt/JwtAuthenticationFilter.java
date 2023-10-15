package com.kusitms.jipbap.security.jwt;

import com.kusitms.jipbap.security.jwt.exception.EmptyTokenException;
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
        if (bearerToken==null) {
            throw new EmptyTokenException("Authorization 헤더에 토큰이 없습니다.");
        }
        String jwtToken = jwtTokenProvider.resolveToken(bearerToken);
        if(jwtToken != null && jwtTokenProvider.validateToken(jwtToken)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else {
            throw new InvalidTokenException("토큰이 유효하지 않습니다.");
        }
        filterChain.doFilter(request, response);
    }
}