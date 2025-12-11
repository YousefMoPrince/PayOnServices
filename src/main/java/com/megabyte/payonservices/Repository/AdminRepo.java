package com.megabyte.payonservices.Repository;

import com.megabyte.payonservices.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<Admin, Long> {
    Admin findByAdminName(String adminName);
    Optional<Admin> findByAdminId(long adminId);
}
