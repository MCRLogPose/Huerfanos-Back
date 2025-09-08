package com.huerfanosSRL.Huerfanosback.auth.repository;

import com.huerfanosSRL.Huerfanosback.auth.model.TwoFAModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TwoFARepository extends JpaRepository<TwoFAModel, Long> {
    Optional<TwoFAModel> findByUser_Id(Long userId);
}
