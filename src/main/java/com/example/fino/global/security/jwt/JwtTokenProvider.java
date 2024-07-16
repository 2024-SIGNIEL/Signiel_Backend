package com.example.fino.global.security.jwt;

import com.example.fino.domain.user.domain.auth.dao.RefreshTokenRepository;
import com.example.fino.domain.user.domain.auth.domain.RefreshToken;
import com.example.fino.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor

public class JwtTokenProvider {

    private final JwtProperties jwtProperties; // 의존성 추가
    private final AuthDetailsService authDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    public String generateAccessToken(String id) { // AcessToken을 만들어주는 메소드
        return generateToken(id, "access", jwtProperties.getAccessExpiration());
        // generateToken에 id, "acess", AcessExp()를 준다
    }

    public String generateRefreshToken(String id) { // refreshToken을 만들어주는 메소드
        String refreshToken = generateToken(id, "refresh", jwtProperties.getRefreshExpiration());
        refreshTokenRepository.save(RefreshToken.builder()
                .username(id)
                .token(refreshToken)
                .timeToLive(jwtProperties.getRefreshExpiration())
                .build());

        return refreshToken;
    }

    private String generateToken(String id, String type, Long exp) {

        // generateAcessToken을 만들어주는 실제 메소드

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
                .setSubject(id)
                .claim("type", type)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + exp * 1000))
                .compact();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearer = request.getHeader(jwtProperties.getHeader());
        return parseToken(bearer);
    }

    public Authentication authentication(String token) {
        UserDetails userDetails = authDetailsService
                .loadUserByUsername(getTokenSubject(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String parseToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith(jwtProperties.getPrefix()))
            return bearerToken.replace(jwtProperties.getPrefix(), "");
        return null;
    }

    public ZonedDateTime getExpiredTime() {
        return ZonedDateTime.now().plusSeconds(jwtProperties.getAccessExpiration());
    }

    private Claims getTokenBody(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expired", e);
        } catch (Exception e) {
            throw new JwtException("Invalid token", e);
        }
    }

    private String getTokenSubject(String token) {
        return getTokenBody(token).getSubject();
    }
}
