package com.project.knowledgebuildbackend.repository;

import com.project.knowledgebuildbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserId(Long userId);
    Optional<User> findByNickname(String nickname);
    Optional<User> findBySnsId(String snsId);
}
