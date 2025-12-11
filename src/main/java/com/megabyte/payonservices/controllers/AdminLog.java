package com.megabyte.payonservices.controllers;

import com.megabyte.payonservices.DTOs.ApiResponse;
import com.megabyte.payonservices.Repository.AdminRepo;
import com.megabyte.payonservices.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admins")
public class AdminLog {
    @Autowired
    private AdminRepo adminRepo;

    @GetMapping
    public List<Admin> findAll() {return adminRepo.findAll();}

    @PostMapping("/Adlogin")
    public ResponseEntity<?> login(@RequestBody Admin admin) {
        Admin found = adminRepo.findByAdminName(admin.getAdminName());

        if (found != null && found.getAdminPassword().equals(admin.getAdminPassword())) {
            return ResponseEntity.ok("Login Success :"+found.getAdminName());
        }else {
            return ResponseEntity.status(401).body("Login Failed");
        }
    }
}
