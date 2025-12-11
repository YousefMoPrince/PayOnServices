package com.megabyte.payonservices.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "admins")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    @Column(name = "admin_name", nullable = false, length = 50)
    private String adminName;
    @Column(name = "admin_password", nullable = false, length = 32)
    private String adminPassword;
}
