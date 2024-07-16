package com.example.fino.domain.user.domain.auth.application;

import com.example.fino.domain.user.domain.user.dao.UserRepository;
import com.example.fino.domain.user.domain.user.domain.User;
import com.example.fino.domain.user.domain.user.domain.type.Role;
import com.example.fino.domain.user.domain.user.dto.UserDto;
import com.example.fino.global.security.auth.AuthDetails;
import com.example.fino.global.security.auth.AuthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthDetailsService authDetailsService;

    @Transactional
    public void register(UserDto userDto) {
        // 회원 가입을 할 시
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        User user = User.builder()
                .username(userDto.getUsername())
                .password(encodedPassword)
                .role(userDto.getRole())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public String authenticate(String username, String password) {
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
        // 실제 토큰 생성 로직 구현
        return username;
    }

    private String getUsernameFromToken(String token) {
        // 실제 토큰에서 사용자명 추출 로직 구현
        return token;
    }


    }

