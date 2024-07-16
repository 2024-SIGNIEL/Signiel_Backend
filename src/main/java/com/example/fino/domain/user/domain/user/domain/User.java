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

    @Column(columnDefinition = "VARCHAR(20)")
    private String username;

    @Column(columnDefinition = "VARCHAR(25)")
    private String password;

    //새로 추가
    private Role role;

}
