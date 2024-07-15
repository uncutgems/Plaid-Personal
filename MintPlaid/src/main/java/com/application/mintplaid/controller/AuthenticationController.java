package com.application.mintplaid.controller;

import com.application.mintplaid.dto.*;
import com.application.mintplaid.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register (@RequestBody RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException {
        RegisterResponse registerResponse = authenticationService.register(registerRequest);

        return new ResponseEntity<>(registerResponse, HttpStatusCode.valueOf(registerResponse.getResponseCode()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authenticationResponse = authenticationService.authentication(authenticationRequest);
        return new ResponseEntity<>(authenticationResponse, HttpStatus.valueOf(authenticationResponse.getResponseCode()));
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
        return new ResponseEntity<>(authenticationService.refreshToken(tokenRefreshRequest.getRefreshToken()), HttpStatus.OK);
    }

}