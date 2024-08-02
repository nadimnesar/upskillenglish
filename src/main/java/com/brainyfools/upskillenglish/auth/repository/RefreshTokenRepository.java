package com.brainyfools.upskillenglish.auth.repository;


import com.brainyfools.upskillenglish.auth.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    RefreshToken findByToken(String refreshToken);
}