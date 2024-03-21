package com.banking_api.banking_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final UserRepository userRepository;

    public TokenService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String tokenGenerate(UserDetails user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
            return JWT.create()
                    .withIssuer("bankingApi")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expireAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao  gerar o token", exception);
        }





    }

    public String getSubject(String tokenJWT) {
        tokenJWT = tokenJWT.trim();

        // Verifica se a string termina com \r\n e remove se necessário
        if (tokenJWT.endsWith("\r\n")) {
            tokenJWT = tokenJWT.substring(0, tokenJWT.length() - 2);
        }

        // Verifica se a string está entre aspas e remove se necessário
        if (tokenJWT.startsWith("\"") && tokenJWT.endsWith("\"")) {
            tokenJWT = tokenJWT.substring(1, tokenJWT.length() - 1);
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
            return JWT.require(algorithm)
                    .withIssuer("bankingApi")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

        private Instant expireAt () {
            return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));

        }

    public String refreshToken(String tokenJWT) {

        String subject = getSubject(tokenJWT);
        var user = userRepository.findByUsername(subject);

        return tokenGenerate(user);
    }





    }

