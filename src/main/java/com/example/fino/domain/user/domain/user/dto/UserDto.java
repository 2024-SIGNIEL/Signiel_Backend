package com.example.fino.domain.user.domain.user.dto;

import com.example.fino.domain.user.domain.user.domain.type.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UserDto {

    private String username;
    private String password;
    private String name;
    private Role role;
}
