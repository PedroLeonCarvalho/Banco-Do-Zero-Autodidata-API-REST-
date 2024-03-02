package com.banking_api.banking_api.domain.user;

import com.banking_api.banking_api.dtos.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String cpf;
    @Column(name="birth_date")
    private Date birthDate;
    private int age;
    private String city;
    private boolean active;
    @Column(unique = true)
    private String username;
    private String password;

    public User(UserDto user) {
        this.id = user.id();
        this.name = user.name();
        this.email = user.email();
        this.cpf = user.cpf();
        this.birthDate = user.birthDate();
        this.age = user.age();
        this.active=true;
        this.city = user.city();
        this.username = user.username();
        this.password = user.password();
    }
}

