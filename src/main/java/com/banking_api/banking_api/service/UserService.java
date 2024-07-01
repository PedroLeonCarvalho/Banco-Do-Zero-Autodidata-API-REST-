package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;

    }
  @CachePut("UserList")
    public UserDto createUser(UserDto data) {
        User newUser = new User(data);
        newUser.setPassword(passwordEncoder.encode(data.password()));
        userRepository.save(newUser);
        return convertToDto(newUser);
    }


    @CachePut("UserList")
    public UserDto updateUser(UserDto data) {

        User user = userRepository.getReferenceById(data.id());

            user.setName(data.name());
            user.setEmail(data.email());
            user.setCpf(data.cpf());
            user.setBirthDate(data.birthDate());
            user.setAge(data.age());
            user.setCity(data.city());
            user.setUsername(data.username());
            user.setPassword(data.password());


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
    @CacheEvict("UserList")
    public void deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        user.setActive(false);
        userRepository.save(user);
    }


  @Cacheable("UserList")
    public Page<List<UserDto>> findAllActiveUsers(Pageable pageable) {
      var page = userRepository.findAllByActiveTrue(pageable);
        return page.map(u-> Collections.singletonList(new UserDto(null, u.getName(), null, null, null,null, null, null, null)));
    }
    @Cacheable("UserById")
    public User findUserById(Long id) throws EntityNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado"));
    }
}


