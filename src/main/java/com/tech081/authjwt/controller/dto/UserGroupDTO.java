package com.tech081.authjwt.controller.dto;

import com.tech081.authjwt.data.UserGroup;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserGroupDTO {

    private Long id;

    @NotEmpty
    private String description;

    private List<RoleDTO> roles = new ArrayList<>();


    public UserGroupDTO() {
    }

    public UserGroupDTO(Long id, String description, List<RoleDTO> roles) {
        this.id = id;
        this.description = description;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public UserGroup toEntity() {
        return new UserGroup(this.getDescription(), this.getRoles().stream().map(RoleDTO::toEntity).collect(Collectors.toList()));
    }
}
