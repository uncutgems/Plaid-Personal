package com.application.mintplaid.repository;

import com.application.mintplaid.entity.RefreshToken;
import com.application.mintplaid.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Transactional
    @Modifying
    int deleteByUser(User user);
}
