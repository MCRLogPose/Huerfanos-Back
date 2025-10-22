package com.bakerysystem.claims.repository;

import com.bakerysystem.claims.model.Claim;
import com.bakerysystem.claims.model.ClaimStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByStatus(ClaimStatus status);
    List<Claim> findByUserId(Long userId);
}
