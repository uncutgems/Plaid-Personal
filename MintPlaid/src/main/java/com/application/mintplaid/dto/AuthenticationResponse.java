package com.application.mintplaid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private Integer responseCode;
    private String accessToken;
    private String refreshToken;
    private Long accessExpire;
    private Long refreshExpire;
    private String email;
    private String username;
    private String role;
    private Boolean enabled;
    private String message;
}
