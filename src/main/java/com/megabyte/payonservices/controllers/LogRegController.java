package com.megabyte.payonservices.controllers;

import com.megabyte.payonservices.DTOs.ApiResponse;
import com.megabyte.payonservices.DTOs.LoginRequest;
import com.megabyte.payonservices.DTOs.RegisterRequest;
import com.megabyte.payonservices.DTOs.UserResponse;
import com.megabyte.payonservices.Repository.UserRepo;
import com.megabyte.payonservices.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/LogReg")
@RequiredArgsConstructor
public class LogRegController {
    private final UserRepo userRepo;


    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@Valid @RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUserName())) {
            return new ApiResponse<>("UserName already exists",null);
        }
    if (userRepo.existsByEmail(req.getEmail())) {
        return new ApiResponse<>("Email already exists",null);
    }
        User user = User.builder().username(req.getUserName()).email(req.getEmail()).password(req.getPassword()).full_name(req.getFullName()).phone(req.getPhone()).build();
    userRepo.save(user);
    UserResponse response = UserResponse.builder().userId(user.getUserId()).username(user.getUsername()).email(user.getEmail()).build();
    return new ApiResponse<>("User registerd Successfully",response);
    }

    @PostMapping("/Login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody LoginRequest req) {
        Optional<User> userOpt = userRepo.findByUsernameOrEmail(req.getUsernameOrEmail(), req.getUsernameOrEmail());
        if (userOpt.isEmpty()) {
            return new ApiResponse<>("Username or email does not exist",null);
        }
        User user = userOpt.get();
        if (!user.getPassword().equals(req.getPassword())) {
            return new ApiResponse<>("Incorrect password",null);
        }
        UserResponse response = UserResponse.builder().userId(user.getUserId()).username(user.getUsername()).email(user.getEmail()).build();
        return new ApiResponse<>("User logged in successfully",response);
    }
}
