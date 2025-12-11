package com.megabyte.payonservices.DTOs;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class RegisterRequest {
    @NotBlank(message = "username required")
    @Size(max = 50)
    private String userName;
    @NotBlank(message = "password required")
    @Size(max = 50)
    private String password;
    @NotBlank(message = "email required")
    @Email(message = "invalid email format")
    private String email;
    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "\\d{11}", message = "Phone must be 11 digits")
    private String phone;
    @NotBlank(message = "Full name is required")
    private String fullName;
}
