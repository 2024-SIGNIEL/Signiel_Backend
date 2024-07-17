package com.example.fino.domain.user.domain.auth.application;

import com.example.fino.domain.user.domain.user.dao.UserRepository;
import com.example.fino.domain.user.domain.user.domain.User;
import com.example.fino.domain.user.domain.user.dto.UserDto;
import com.example.fino.global.security.auth.AuthDetails;
import com.example.fino.global.security.auth.AuthDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthDetailsService authDetailsService;
    private final String SECRET_KEY = "your_secret_key";  // 비밀키

    @Transactional
    public void register(UserDto userDto) {

        String name = userDto.getName();

        if(name.length() > 4){
            throw new IllegalArgumentException("Username length must not exceed 4 characters");
        }

        userRepository.findByUsername(userDto.getUsername())
                .ifPresent(user -> { throw new IllegalStateException("Username already taken"); });


        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = User.builder()
                .username(userDto.getUsername())
                .password(encodedPassword)
                .name(userDto.getName())
                .role(userDto.getRole())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public String authenticate(String username, String password) {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Username or password is empty");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return generateToken(username);
    }

    public boolean authorize(String token) {
        String username = getUsernameFromToken(token);
        AuthDetails userDetails = (AuthDetails) authDetailsService.loadUserByUsername(username);
        return userDetails != null;
    }

    private String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 86400000); // 토큰 유효기간 1일

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    private String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
