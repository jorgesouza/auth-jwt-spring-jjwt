package com.tech081.authjwt.controller.dto;

import com.tech081.authjwt.data.User;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class UserDTO {

    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private UserGroupDTO userGroup;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, String password, LocalDateTime createdAt, UserGroupDTO userGroup) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.createdAt = createdAt;
        this.userGroup = userGroup;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public UserGroupDTO getUserGroup() {
        return userGroup;
    }

    public User toEntity() {
        return new User(this.getName(), this.getEmail(), this.getPassword());
    }
}
