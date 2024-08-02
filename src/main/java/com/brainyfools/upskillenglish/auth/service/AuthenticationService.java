package com.brainyfools.upskillenglish.auth.service;

import com.brainyfools.upskillenglish.auth.dto.ResponseDto;
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
    private final RefreshTokenService refreshTokenService;

    public AuthenticationService(PasswordEncoder passwordEncoder, UserRepository userRepository,
                                 JwtService jwtService, AuthenticationManager authenticationManager,
                                 RefreshTokenService refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.refreshTokenService = refreshTokenService;
    }

    public boolean invalidUserDto(UserDto userDto) {
        if(userDto.getUsername() == null || userDto.getUsername().isEmpty()) return true;
        if(userDto.getPassword() == null || userDto.getPassword().isEmpty()) return true;
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

        if (invalidUserDto(userDto)){
            response.put("message", "Please provide both username and password.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        User user = userRepository.findByUsername(userDto.getUsername());
        if (user == null){
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
        String refreshToken = refreshTokenService.generateRefreshToken(user.getUsername());

        ResponseDto responseDto = new ResponseDto(jwtToken, refreshToken, user.getRole());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    public ResponseEntity<?> refresh(String token) {
        Map<String, Object> response = new HashMap<>();

        if (refreshTokenService.isValid(token)) {
            String jwtToken = refreshTokenService.getJwtToken(token);
            response.put("jwt", jwtToken);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        response.put("message", "Refresh token invalid or expired.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}