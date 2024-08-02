package com.brainyfools.upskillenglish.auth.dto;


import com.brainyfools.upskillenglish.auth.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String jwt;
    private String refreshToken;
    private UserRole userRole;
}