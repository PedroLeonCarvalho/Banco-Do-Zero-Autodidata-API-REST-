package com.banking_api.banking_api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.banking_api.banking_api.domain.user.User;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {


    public String tokenGenerate(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
            return JWT.create()
                    .withIssuer("bankingApi")
                    .withSubject(user.getUsername())
                    .withExpiresAt(expireAt())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao  gerar o token", exception);
        }
    }

    public String getSubject(String tokenJWT) {
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
    }

