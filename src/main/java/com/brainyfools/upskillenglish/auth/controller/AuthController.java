package com.brainyfools.upskillenglish.auth.controller;

import com.brainyfools.upskillenglish.auth.dto.UserDto;
import com.brainyfools.upskillenglish.auth.enums.UserRole;
import com.brainyfools.upskillenglish.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> getLogin(@RequestBody UserDto userDto) {
        return authenticationService.login(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegister(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRole.USER);
    }

}