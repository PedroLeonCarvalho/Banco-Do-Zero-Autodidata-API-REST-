package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.account.Account;
import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
     void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createUser() {
        // Arrange
        UserDto userDto = new UserDto(null, "John Doe", "john@example.com", "12345678901",
                LocalDate.of(1990, 1, 1), 30, "City", "john_doe", "password");

        User newUser = new User(userDto);

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        UserDto createdUserDto = userService.createUser(userDto);

        // Assert
        assertNotNull(createdUserDto);
        assertEquals(newUser.getId(), createdUserDto.id());
        assertEquals(newUser.getName(), createdUserDto.name());
        assertEquals(newUser.getEmail(), createdUserDto.email());
        assertEquals(newUser.getCpf(), createdUserDto.cpf());
        assertEquals(newUser.getBirthDate(), createdUserDto.birthDate());
        assertEquals(newUser.getAge(), createdUserDto.age());
        assertEquals(newUser.getCity(), createdUserDto.city());
        assertEquals(newUser.getUsername(), createdUserDto.username());
        assertEquals(newUser.getPassword(), createdUserDto.password());
    }

    @Test
    void updateUser() {
        // Arrange
        UserDto userDto = new UserDto(1L, "Updated Name", null, null,
                null, 0, null, null, null);

        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Old Name");
        when(userRepository.getReferenceById(1L)).thenReturn(existingUser);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserDto updatedUserDto = userService.updateUser(userDto);

        // Assert
        assertNotNull(updatedUserDto);
        assertEquals(userDto.id(), updatedUserDto.id());
        assertEquals(userDto.name(), updatedUserDto.name());
    }

    @Test
    void deactivateUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setActive(true);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deactivateUser(userId);

        // Assert
        assertFalse(user.isActive());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void findAllActiveUsers() {
        // Arrange

        List<User> userList = new ArrayList<>();
        List<Account> accountsUser1 = new ArrayList<>();
        List<Account> accountsUser2 = new ArrayList<>();
        userList.add(new User (1L, "John", "john@example.com", "12345678901",
                LocalDate.of(1990, 1, 1), 30, "City", true, "john_doe", "password",accountsUser1));

        userList.add(new User(2L, "Jane", "jane@example.com", "23456789012",
                LocalDate.of(1995, 1, 1), 25, "City",true, "jane_doe", "password",accountsUser2));

        Page<User> userPage = new PageImpl<>(userList);


        when(userRepository.findAllByActiveTrue(any(Pageable.class))).thenReturn(userPage);

        // Act
        Page<List<UserDto>> resultPage = userService.findAllActiveUsers(Pageable.unpaged());

        // Assert
        assertNotNull(resultPage);
        assertEquals(userList.size(), resultPage.getContent().size());
    }

    @Test
    void findUserById() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setName("John");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User resultUser = userService.findUserById(userId);

        // Assert
        assertNotNull(resultUser);
        assertEquals(user.getId(), resultUser.getId());
        assertEquals(user.getName(), resultUser.getName());
    }

    @Test
    void findUserById_notFound() {
        // Arrange
        Long wrongUserId = 1L;

        when(userRepository.findById(wrongUserId)).thenReturn(Optional.empty());

        // Act


        // Assert

        assertThrows( EntityNotFoundException.class, () -> {
            userService.findUserById(wrongUserId);
        }, "Usuário com ID "+wrongUserId+ "não encontrado");

    }
}

