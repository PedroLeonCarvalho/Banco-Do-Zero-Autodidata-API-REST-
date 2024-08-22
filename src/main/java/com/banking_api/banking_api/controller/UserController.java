package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/users")
@Tag(name = "User / Usuário ", description = "Criar, excluir, atualizar e listar usuários")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create new user/ Criar novo usuário", description = " Create new user")
    public ResponseEntity<UserDto> createUser(@RequestBody  @Valid UserDto user) {
       var newUser = userService.createUser(user);
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Update / Atualizar ", description = " Update data from user - must be logged / atualiza dados do usuário - precisa estar logado")

    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto data) {
        UserDto user = userService.updateUser(data);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Find user by ID / Encontra usuário pelo ID ", description = " Must be logged /  precisa estar logado")

    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    @Operation(summary = "List active users / Lista todos usuários ativos ", description = " Must be logged / Precisa estar logado / Input : {\n" +
            "  \"page\": 0,\n" + "  \"size\": 9,\n" + "  \"sort\": [\n" + "    \n" + "  ]\n" + "}")
    public ResponseEntity<Page<List<UserDto>>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<List<UserDto>> page = userService.findAllActiveUsers(pageable);
        return ResponseEntity.ok(page);

    }


}
