package com.brainyfools.upskillenglish.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    @GetMapping("/")
    public ResponseEntity<?> getUserPage() {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("massage", "Welcome to User page!");
        return new ResponseEntity<>(hashMap, HttpStatus.ACCEPTED);
    }
}