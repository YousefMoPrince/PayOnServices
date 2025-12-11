package com.megabyte.payonservices.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "username or email required")
    private String usernameOrEmail;
    @NotBlank(message = "password required")
    private String password;
}
