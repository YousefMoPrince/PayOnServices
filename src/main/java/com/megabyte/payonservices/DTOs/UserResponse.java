package com.megabyte.payonservices.DTOs;

import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Bean;


@Data
@Builder
public class UserResponse {
    private Long userId;
    private String username;

    private String email;
}
