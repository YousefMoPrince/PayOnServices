package com.megabyte.payonservices.DTOs;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String phone;
    private String email;
    private String password;
}
