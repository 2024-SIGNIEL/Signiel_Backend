package com.example.fino.domain.user.domain.auth.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String username;
    private String token;
    private Long timeToLive;

    @Builder
    public RefreshToken(String username, String token, Long timeToLive) {
        this.username = username;
        this.token = token;
        this.timeToLive = timeToLive;
    }
}
