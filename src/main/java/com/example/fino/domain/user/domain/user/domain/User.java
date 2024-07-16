package com.example.fino.domain.user.domain.user.domain;

import com.example.fino.domain.user.domain.user.domain.type.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;


    private String password;

    //새로 추가
    private Role role;

}
