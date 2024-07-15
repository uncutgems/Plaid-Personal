package com.application.mintplaid.service;

import com.application.mintplaid.config.Constant;
import com.application.mintplaid.config.JwtService;
import com.application.mintplaid.dto.*;
import com.application.mintplaid.entity.RefreshToken;
import com.application.mintplaid.entity.User;
import com.application.mintplaid.repository.RefreshTokenRepository;
import com.application.mintplaid.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceInterface{
    @Value("${application.security.jwt.refresh-token.expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final JavaMailSender mailSender;

    public RegisterResponse register(@NotNull RegisterRequest registerRequest) throws MessagingException, UnsupportedEncodingException {


        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return RegisterResponse.builder().responseCode(409).message("Email or username is already exist").build();
        }
        if(userRepository.findUserByUsername(registerRequest.getUsername()).isPresent())
            return RegisterResponse.builder().responseCode(409).message("Username is already in use").build();
        User user = User.builder()
                .email(registerRequest.getEmail())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(User.Role.USER)
                .enabled(false)
                .build();
        user.setVerificationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendVerificationEmail(user);
        return RegisterResponse.builder().responseCode(200).message("The Registration is successful").build();
    }

    public AuthenticationResponse authentication(@NotNull AuthenticationRequest authenticationRequest) {
        long currentTime = System.currentTimeMillis();
        User user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        if (!user.getEnabled()) {
            return AuthenticationResponse.builder().responseCode(409)
                    .message("Email address is not verified. Please verify using provided link").build();
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        if (authentication.isAuthenticated())
            refreshTokenRepository.deleteByUser(user);

        String jwtToken = jwtService.generateToken(user);
        RefreshToken refreshToken = createRefreshToken(user.getId());

        return AuthenticationResponse.builder()
                .responseCode(200)
                .message("Login Successful")
                .accessToken(jwtToken).refreshToken(refreshToken.getRefreshToken())
                .accessExpire(currentTime + 3600000).refreshExpire(currentTime + 86400000)
                .email(user.getEmail()).role(String.valueOf(user.getRole()))
                .username(user.getUsername())
                .enabled(user.getEnabled()).build();
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(refreshToken::setUser);
        refreshToken.setExpiry(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(@NotNull RefreshToken token) throws TokenRefreshException {
        if (token.getExpiry().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getRefreshToken(),
                    "Refresh token was expired. Please make a new sign-in request");
        }

        return token;
    }

    public TokenRefreshResponse refreshToken(String token) {
        long currentTime = System.currentTimeMillis();
        return refreshTokenRepository.findByRefreshToken(token)
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    refreshTokenRepository.deleteByUser(user);
                    return new TokenRefreshResponse(jwtService.generateToken(user),
                            createRefreshToken(user.getId()).getRefreshToken(),
                            currentTime + 3600000, currentTime + 86400000, user.getEmail()
                            , String.valueOf(user.getRole()), user.getEnabled());
                }).orElseThrow(() -> new TokenRefreshException(token, "Refresh token does not exist or has expired"));
    }

    public void sendVerificationEmail(@NotNull User user) throws MessagingException, UnsupportedEncodingException {
        String clientEmail = user.getEmail();
        String fromAddress = "quangdinhtesting@gmail.com";
        String senderName = "MintPlaidByQ";
        String subject = "Please verify your registration";
        String content = "Dear " + user.getUsername()
                + ", <br>" + "Please click the link below to verify your registration: <br>"
                + "<h3> <a href=\"" + Constant.localDevelopment + "/verify?code=" + user.getVerificationCode() + "\" target=\"_self\"> VERIFY </a></h3>"
                + "Thank you, <br>"
                + senderName;
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(clientEmail);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    public String verifyEmail(String verificationCode) {
        Optional<User> optionalUser = userRepository.findUserByVerificationCode(verificationCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            return "User Email Verification is completed. You can now close this tab";
        }
        return "The Verification Code is invalid, please register again";
    }

}
