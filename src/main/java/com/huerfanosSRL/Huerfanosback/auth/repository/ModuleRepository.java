package com.huerfanosSRL.Huerfanosback.auth.repository;

import com.huerfanosSRL.Huerfanosback.auth.model.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleModel, Long> {
    Optional<ModuleModel> findByNameModule(String nameModule);
}
