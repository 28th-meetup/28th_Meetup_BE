package com.kusitms.jipbap.security.jwt;

import com.kusitms.jipbap.security.jwt.exception.InvalidTokenException;
import com.kusitms.jipbap.user.model.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final long ACCESS_TOKEN_VALID_TIME = (1000*60*60*24); //day
    private final long REFRESH_TOKEN_VALID_TIME = (1000*60*60*24*7); //week
    private final String BEARER_TYPE = "Bearer ";

    @Value("${secret.jwt}")
    private String baseSecretKey;

    public TokenInfo createAccessToken(String email, Role role) {
        return createToken(email, role, ACCESS_TOKEN_VALID_TIME);
    }

    public TokenInfo createRefreshToken(String email, Role role) {
        return createToken(email, role, REFRESH_TOKEN_VALID_TIME);
    }

    public Boolean validateToken(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(baseSecretKey));
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException e) {
            throw new InvalidTokenException("Jwt Security error");
        } catch (MalformedJwtException e) {
            throw new InvalidTokenException("MalformedJwtException - 잘못된 Jwt Token");
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("ExpiredJwtException - 만료된 Jwt Token");
        } catch (UnsupportedJwtException e) {
            throw new InvalidTokenException("UnsupportedJwtException - 지원하지 않는 Jwt Token");
        } catch (IllegalArgumentException e) {
            throw new InvalidTokenException("IllegalArgumentException - 잘못된 헤더");
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new InvalidTokenException("SignatureException - 잘못된 Token 형식");
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        try {
            claims.get("email");
        } catch(Exception e) {
            try {
                throw new AuthenticationException("Jwt 토큰에 이메일이 존재하지 않습니다.");
            } catch (AuthenticationException ex) {
                ex.printStackTrace();
            }
        }
        Collection<GrantedAuthority> authorities =
                Arrays.stream(claims.get("ROLE_").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails userDetails = new User(claims.get("email").toString(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String resolveToken(String token) {
        if(token.startsWith("Bearer ")) {
            return token.replace("Bearer ", "");
        }
        return null;
    }

    public Claims parseClaims(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(baseSecretKey));
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    private TokenInfo createToken(String email, Role role, long validTime) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validTime);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(baseSecretKey));

        String token = Jwts.builder()
                .claim("email", email)
                .claim("ROLE_", role)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        token = BEARER_TYPE + token;
        return new TokenInfo(token, expiration.getTime() - now.getTime());
    }
}
