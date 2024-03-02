package com.banking_api.banking_api.service;

import com.banking_api.banking_api.domain.user.User;
import com.banking_api.banking_api.dtos.UserDto;
import com.banking_api.banking_api.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }





    public User createUser(UserDto data) {
        User newUser = new User(data);
        userRepository.save(newUser);
        return newUser;
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

        userRepository.save(user);

      return convertToDto(user);

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
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        user.setActive(false);
        userRepository.save(user);
    }

    private UserDto convertToListDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public Page<UserDto> findAllActiveUsers(Pageable pageable) {
        Page<User> page = userRepository.findAllByActiveTrue(pageable);
        return page.map(user -> modelMapper.map(user, UserDto.class));
    }
}


