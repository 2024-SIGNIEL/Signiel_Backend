package com.example.fino.domain.user.domain.auth.application;

import com.example.fino.domain.user.domain.user.dao.UserRepository;
import com.example.fino.domain.user.domain.user.domain.User;
import com.example.fino.domain.user.domain.user.domain.type.Role;
import com.example.fino.global.security.auth.AuthDetails;
import com.example.fino.global.security.auth.AuthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService { // AuthController의 요청을 처리해줌

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthDetailsService authDetailsService;

    public void register(String username, String password, Role role) {
        // 회원 가입을 할 시
        if (userRepository.findByUsername(username).isPresent()) {  // 파라미터로 들어온 유저 아이디가 이미 있는지 검증
            throw new IllegalStateException("Username already taken");
        }

        String encodedPassword = passwordEncoder.encode(password); // 비밀번호 암호화
        User user = User.builder() // User Entity에다가 유저의 정보를 저장함
                .username(username)
                .password(encodedPassword)
                .role(role).build();
        userRepository.save(user);
    }

    public String authenticate(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return generateToken(username); // 토큰 만들어줌
    }

    public boolean authorize(String token) { // 인증
        String username = getUsernameFromToken(token); // token을 통해서 username을 가져옴
        AuthDetails userDetails = (AuthDetails) authDetailsService.loadUserByUsername(username);
        // username을 통해서 user의 정보를 userDetails의 형태로 객체를 생성한다.
        return userDetails != null;
        // userDetails가 없으면 거짓을 반환 / 있으면 참을 반환
    }

    private String generateToken(String username) { // 반환?
        // 토큰 생성 로직을 추가합니다. 여기서는 단순히 사용자명을 반환합니다.
        return username;
    }

    private String getUsernameFromToken(String token) {
        // 토큰에서 사용자명을 추출하는 로직을 추가합니다. 여기서는 단순히 토큰을 반환합니다.
        return token;
    }
}
