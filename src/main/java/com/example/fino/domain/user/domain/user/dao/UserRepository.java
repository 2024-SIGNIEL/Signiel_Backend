package com.example.fino.domain.user.domain.user.dao;

import com.example.fino.domain.user.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

}
