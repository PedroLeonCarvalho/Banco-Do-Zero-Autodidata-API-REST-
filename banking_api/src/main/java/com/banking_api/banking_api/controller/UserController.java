package com.banking_api.banking_api.controller;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.dtos.UserDtoList;
import com.banking_api.banking_api.repository.UserRepository;
import com.banking_api.banking_api.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto data) {
         UserDto user = userService.updateUser(data);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<String> deactivateUser(@PathVariable Long id) {
        userService.deactivateUser(id);
        return ResponseEntity.ok("Usuáriao desativado com sucesso");

    }

    @GetMapping
    public ResponseEntity<Page<UserDtoList>> list(@PageableDefault(size = 10) Pageable pageable) {
        Page<UserDtoList> page = userService.findAllActiveUsers(pageable);
        return ResponseEntity.ok(page);


    }


}
