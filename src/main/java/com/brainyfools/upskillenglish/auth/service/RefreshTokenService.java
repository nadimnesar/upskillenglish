package com.brainyfools.upskillenglish.auth.service;


import com.brainyfools.upskillenglish.auth.model.RefreshToken;
import com.brainyfools.upskillenglish.auth.model.User;
import com.brainyfools.upskillenglish.auth.repository.RefreshTokenRepository;
import com.brainyfools.upskillenglish.auth.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtService = jwtService;
    }

    public boolean isValid(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        System.out.println(refreshToken.getExpiryDate());
        System.out.println(Instant.now());
        return refreshToken.getExpiryDate().compareTo(Instant.now()) > 0;
    }

    public String getJwtToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token);
        User user = refreshToken.getUser();
        return jwtService.generateToken(user);
    }

    public String generateRefreshToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user.getRefreshToken() != null) {
            RefreshToken refreshToken = refreshTokenRepository.findByToken(user.getRefreshToken().getToken());
            refreshToken.setToken(UUID.randomUUID().toString()); //new refresh token
            refreshToken.setExpiryDate(Instant.now().plusMillis(86400000)); //1 day extend
            refreshTokenRepository.save(refreshToken);

            user.setRefreshToken(refreshToken);
            userRepository.save(user);

            return refreshToken.getToken();
        }

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setExpiryDate(Instant.now().plusMillis(86400000)); //1 day
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setUser(user);
        refreshTokenRepository.save(refreshToken);

        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return refreshToken.getToken();
    }
}