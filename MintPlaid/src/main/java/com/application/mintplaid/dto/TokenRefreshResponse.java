package com.application.mintplaid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenRefreshResponse {
    private String accessToken;
    private String refreshToken;
    private Long accessExpire;
    private Long refreshExpire;
    private String email;
    private String role;
    private Boolean enabled;
}
