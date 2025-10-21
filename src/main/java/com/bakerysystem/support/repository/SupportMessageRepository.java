package com.bakerysystem.support.repository;

import com.bakerysystem.support.model.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, Long> {
    List<SupportMessage> findByUserIdOrderByCreatedAtDesc(Long userId);
}