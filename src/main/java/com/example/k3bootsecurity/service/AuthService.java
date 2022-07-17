package com.example.k3bootsecurity.service;

import com.example.k3bootsecurity.domain.JwtRequest;
import com.example.k3bootsecurity.domain.JwtResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDetailsService service;
    private final JwtProvider jwtProvider;
    private final Map<String, String> refreshStorage = new HashMap<>();

    public JwtResponse login(JwtRequest authRequest) throws AuthException {
        final UserDetails userDetails = service.loadUserByUsername(authRequest.getLogin());
        if (userDetails.getUsername().equals(authRequest.getLogin())) {
            final String accessToken = jwtProvider.generateAccessToken(userDetails);
            final String refreshToken = jwtProvider.generateRefreshToken(userDetails);
            refreshStorage.put(userDetails.getUsername(), refreshToken);
            log.info("login");
            refreshStorage.entrySet().stream().forEach(stringStringEntry -> log.info(stringStringEntry.toString()));
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDetails userDetails = service.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(userDetails);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse refresh(String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDetails userDetails = service.loadUserByUsername(login);
                final String accessToken = jwtProvider.generateAccessToken(userDetails);
                final String newRefreshToken = jwtProvider.generateRefreshToken(userDetails);
                refreshStorage.put(userDetails.getUsername(), newRefreshToken);
                log.info("refresh");
                refreshStorage.entrySet().stream().forEach(stringStringEntry -> log.info(stringStringEntry.toString()));
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public Authentication getAuthInfo() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
