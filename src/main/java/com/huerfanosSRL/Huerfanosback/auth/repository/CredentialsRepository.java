package com.huerfanosSRL.Huerfanosback.auth.repository;

import com.huerfanosSRL.Huerfanosback.auth.model.CredentialsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CredentialsRepository extends JpaRepository<CredentialsModel, Long> {
    Optional<CredentialsModel> findByUser_Id(Long userId);
}