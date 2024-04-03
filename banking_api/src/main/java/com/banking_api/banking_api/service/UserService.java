package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public UserDto createUser(UserDto data) {
        User newUser = new User(data);
        userRepository.save(newUser);

        return convertToDto(newUser);
    }

    public UserDto updateUser(UserDto data) {

        User user = userRepository.getReferenceById(data.id());

        if (data.name() != null) {
            user.setName(data.name());
        }

        if (data.email() != null) {
            user.setEmail(data.email());
        }

        if (data.cpf() != null) {
            user.setCpf(data.cpf());
        }

        if (data.birthDate() != null) {
            user.setBirthDate(data.birthDate());
        }

        if (data.age() != 0) {
            user.setAge(data.age());
        }

        if (data.city() != null) {
            user.setCity(data.city());
        }

        if (data.username() != null) {
            user.setUsername(data.username());
        }

        if (data.password() != null) {
            user.setPassword(data.password());
        }

        User updatedUser = userRepository.save(user);
        var userDtoUpdated = convertToDto(updatedUser);

        return userDtoUpdated;

    }


    public UserDto convertToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getBirthDate(),
                user.getAge(),
                user.getCity(),
                user.getUsername(),
                user.getPassword());
    }

    @Transactional
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        user.setActive(false);
        userRepository.save(user);
    }



    public Page<List<UserDto>> findAllActiveUsers(Pageable pageable) {
      var page = userRepository.findAllByActiveTrue(pageable);
        return page.map(u-> Collections.singletonList(new UserDto(null, u.getName(), null, null, null,null, null, null, null)));
    }

    public User findUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado"));
    }
}


