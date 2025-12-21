package com.megabyte.payonservices.Repository;

import com.megabyte.payonservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUserId(Long userId);
    Optional<User> findByPhone(String phone);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
