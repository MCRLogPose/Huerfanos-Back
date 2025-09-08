package com.huerfanosSRL.Huerfanosback.auth.repository;

import com.huerfanosSRL.Huerfanosback.auth.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> findByEmail(String email);

    List<UserModel> findByRole_NameIgnoreCase(String roleName);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
