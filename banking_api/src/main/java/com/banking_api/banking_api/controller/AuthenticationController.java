package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.dtos.LoginDTO;
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

    public AuthenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity login (@RequestBody LoginDTO dto) {

        var token = new UsernamePasswordAuthenticationToken(dto.password(),dto.username());
        var o =authenticationManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
