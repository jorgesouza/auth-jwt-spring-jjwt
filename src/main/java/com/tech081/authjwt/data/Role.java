package com.tech081.authjwt.data;

import com.tech081.authjwt.controller.dto.RoleDTO;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    public Role() {
    }

    public Role(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleDTO toDTO() {
        return new RoleDTO(this.getId(), this.getDescription());
    }
}
