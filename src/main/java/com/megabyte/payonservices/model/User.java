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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "username",length = 50, nullable = false)
    private String username;
    @Column(name = "password", length = 32, nullable = false)
    private String password;
    @Column(name = "full_name", length = 255, nullable = false)
    private String full_name;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "phone", length = 11, nullable = false)
    private String phone;


}
