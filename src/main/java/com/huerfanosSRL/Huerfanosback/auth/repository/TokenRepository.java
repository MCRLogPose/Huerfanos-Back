package com.huerfanosSRL.Huerfanosback.auth.repository;

import com.huerfanosSRL.Huerfanosback.auth.model.TokenModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenModel, Long> {
    Optional<TokenModel> findByRefreshToken(String refreshToken);
    List<TokenModel> findByUser_IdAndRevokedFalseAndExpiredFalse(Long userId);
}
