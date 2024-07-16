package com.example.fino.domain.user.domain.auth.api;

import com.example.fino.domain.user.domain.auth.application.AuthService;
import com.example.fino.domain.user.domain.user.domain.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(String username, String password, Role role) {
        authService.register(username, password, role);
        return ResponseEntity.ok("User registered successfully"); // 유저 인증 성공 여부 반환
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(String username, String password) {
        String token = authService.authenticate(username, password);
        return ResponseEntity.ok(token); // 유저 인가 성공 여부 반환(token을 통해서 반환)
    }
}
