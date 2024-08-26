package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.LoginDTO;
import com.banking_api.banking_api.infra.security.TokenDTO;
import com.banking_api.banking_api.infra.security.TokenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/login")
@Tag(name = "Fazer login/ Log in ", description = "Antes você precisa criar uma conta de Usuário/ Before, you must create new User account")

public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;


    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;

    }

    @PostMapping
    @Operation(summary = "Fazer login/ Log in ", description = "Insira os dados senha e username cadastrados/ Insert registered password and username")

    public ResponseEntity login(@RequestBody LoginDTO dto) {

        var token = new UsernamePasswordAuthenticationToken(dto.username(), dto.password());
        var authentication = authenticationManager.authenticate(token);
        var tokenJWT = tokenService.tokenGenerate((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(tokenJWT));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh Token ", description = "Obtenha novo token de autenticação/  Get a new authentication Token")

    public ResponseEntity<TokenDTO> refreshToken(@RequestBody String tokenJWT) {

        var newTokenJWT = tokenService.refreshToken(tokenJWT);
        return ResponseEntity.ok(new TokenDTO(newTokenJWT));
    }


}