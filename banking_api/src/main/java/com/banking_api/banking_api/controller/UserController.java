package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.service.UserService;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody  @Valid UserDto user) {
       var newUser = userService.createUser(user);
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto data) {
        UserDto user = userService.updateUser(data);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.noContent().build();

    }

    @GetMapping
    public ResponseEntity<Page<List<UserDto>>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<List<UserDto>> page = userService.findAllActiveUsers(pageable);
        return ResponseEntity.ok(page);


    }




}
