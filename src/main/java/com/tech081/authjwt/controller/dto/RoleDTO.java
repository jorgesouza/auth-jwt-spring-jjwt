package com.tech081.authjwt.controller.dto;

import com.tech081.authjwt.data.Role;

import javax.validation.constraints.NotEmpty;

public class RoleDTO {

    private Long id;

    @NotEmpty
    private String description;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Role toEntity() {
        return new Role(this.getDescription());
    }
}
