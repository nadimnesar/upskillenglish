package com.brainyfools.upskillenglish.auth.service;

import com.brainyfools.upskillenglish.auth.dto.UserDto;
import com.brainyfools.upskillenglish.auth.enums.UserRole;
import com.brainyfools.upskillenglish.auth.model.User;
import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                 JwtService jwtService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public boolean invalidUserDto(UserDto userDto) {
        if (userDto.getUsername() == null || userDto.getUsername().isEmpty()) return true;
        if (userDto.getPassword() == null || userDto.getPassword().isEmpty()) return true;
        return false;
    }

    public ResponseEntity<?> register(UserDto userDto, UserRole role) {
        Map<String, Object> response = new HashMap<>();

        if (invalidUserDto(userDto)) {
            response.put("message", "Please provide both username and password.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        try {
            userRepository.save(user);
        } catch (Exception e) {
            response.put("message", "Username already exists.");
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        response.put("message", String.format("%s registered successfully.", role.name()));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> login(UserDto userDto) {
        Map<String, Object> response = new HashMap<>();

        if (invalidUserDto(userDto)) {
            response.put("message", "Please provide both username and password.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null) {
            response.put("message", "User not found.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDto.getUsername(), userDto.getPassword());
            authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            response.put("message", "Invalid password.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        String jwtToken = jwtService.generateToken(user);
        response.put("jwt", jwtToken);
        response.put("username", user.getUsername());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<?> isValidJwt(String jwt) {
        Map<String, Object> response = new HashMap<>();

        try {
            String username = jwtService.extractUsername(jwt);
            User user = userRepository.findByUsername(username);
            if (user == null) {
                response.put("status", "false");
                response.put("message", "User not found in the database");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("status", "false");
            response.put("message", "Invalid token or unable to extract username");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        if (jwtService.isTokenExpired(jwt)) {
            response.put("status", "false");
            response.put("message", "Token is expired.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        response.put("status", "true");
        response.put("message", "Token is valid");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}