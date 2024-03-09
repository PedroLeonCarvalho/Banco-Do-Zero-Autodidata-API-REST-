package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.LoginDTO;
import com.banking_api.banking_api.infra.security.TokenDTO;
import com.banking_api.banking_api.infra.security.TokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody LoginDTO dto) {

        var token = new UsernamePasswordAuthenticationToken(dto.password(), dto.username());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.tokenGenerate((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }
}
