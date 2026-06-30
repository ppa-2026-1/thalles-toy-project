package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 

import com.example.demo.model.dto.LoginDTO;
import com.example.demo.repository.AuthTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.AuthToken;

@Service
public class AuthService {
    private final AuthTokenRepository authTokenRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(
        AuthTokenRepository authTokenRepository,
        UserRepository userRepository
    ) {
        this.authTokenRepository = authTokenRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional(readOnly = true)
    public String validateToken(String token) {

        var authTokenOpt = authTokenRepository.findByToken(token);

        if (authTokenOpt.isEmpty()) {
            return null;
        }

        var authToken = authTokenOpt.get();

        if (authToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return null;
        }

        return authToken.getUser().getHandle();
    }

    public String login(LoginDTO loginDTO) {

        var userOpt = userRepository.findByHandle(loginDTO.username());

        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }

        var user = userOpt.get();

        if (!passwordEncoder.matches(
            loginDTO.password(),
            user.getPassword())) {

            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }

        var authToken = new AuthToken();

        authToken.setToken(UUID.randomUUID().toString());
        authToken.setUser(user);
        authToken.setExpiresAt(LocalDateTime.now().plusHours(24));

        authTokenRepository.save(authToken);

        return authToken.getToken();
    }

    @Transactional
    public void logout(String token) {
        var authTokenOpt = authTokenRepository.findByToken(token);

        if (authTokenOpt.isPresent()) {
            authTokenRepository.delete(authTokenOpt.get());
        }
    }

    @Transactional(readOnly = true)
    public String getHandleByToken(String tokenString) {
        return authTokenRepository.findByToken(tokenString)
            .map(token -> {
                return token.getUser().getHandle();
            })
            .orElse(null); 
    }
}
