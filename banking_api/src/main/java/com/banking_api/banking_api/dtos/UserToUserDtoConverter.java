package com.banking_api.banking_api.dtos;

import com.banking_api.banking_api.domain.user.User;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(MappingContext<User, UserDto> context) {
        User source = context.getSource();
        return new UserDto(
                source.getId(),
                source.getName(),
                source.getEmail(),
                source.getCpf(),
                source.getBirthDate(),
                source.getAge(),
                source.getCity(),
                source.getUsername(),
                source.getPassword()
        );
    }
}

