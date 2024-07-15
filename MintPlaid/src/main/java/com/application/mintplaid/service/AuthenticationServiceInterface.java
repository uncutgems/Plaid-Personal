package com.application.mintplaid.service;

import com.application.mintplaid.dto.AuthenticationRequest;
import com.application.mintplaid.dto.AuthenticationResponse;
import com.application.mintplaid.dto.RegisterRequest;
import com.application.mintplaid.dto.RegisterResponse;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthenticationServiceInterface {
    RegisterResponse register(RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException;
    AuthenticationResponse authentication(AuthenticationRequest registerRequest);

}
