package com.example.fino.domain.user.domain.auth.api;

import com.example.fino.domain.user.domain.auth.application.AuthService;
import com.example.fino.domain.user.domain.user.domain.type.Role;
import com.example.fino.domain.user.domain.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        authService.register(new UserDto(userDto.getUsername(), userDto.getPassword(), userDto.getName(),
                Role.BASIC));
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        String token = authService.authenticate(userDto.getUsername(), userDto.getPassword());
        return ResponseEntity.ok(token);
    }
}
