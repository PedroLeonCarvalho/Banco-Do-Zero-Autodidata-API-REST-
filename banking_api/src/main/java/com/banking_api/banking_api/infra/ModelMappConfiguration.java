package com.banking_api.banking_api.infra;


import com.banking_api.banking_api.dtos.UserToUserDtoConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMappConfiguration  {

    @Autowired
    private UserToUserDtoConverter userToUserDtoConverter;

//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(userToUserDtoConverter);
        return modelMapper;
    }
}