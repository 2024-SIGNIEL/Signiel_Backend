package com.example.fino;

import com.example.fino.global.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class FinoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinoApplication.class, args);
    }

}
