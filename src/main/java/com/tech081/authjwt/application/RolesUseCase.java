package com.tech081.authjwt.application;

import com.tech081.authjwt.controller.dto.RoleDTO;
import com.tech081.authjwt.data.Role;
import com.tech081.authjwt.data.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolesUseCase {

    private final RoleRepository roleRepository;

    public RolesUseCase(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<List<RoleDTO>> findAll() {
        final var roles = this.roleRepository.findAll();

        return Optional.of(roles
                .stream()
                .map(Role::toDTO)
                .collect(Collectors.toList())
        );
    }

    public RoleDTO findById(Long id) {
        final Role role = this.getRole(id);

        return role.toDTO();
    }

    public RoleDTO save(RoleDTO roleDTO) {
        final var role = roleDTO.toEntity();
        final var newRole = this.roleRepository.save(role);

        return newRole.toDTO();
    }

    public RoleDTO update(Long id, RoleDTO roleDTO) {
        final Role role = this.getRole(id);

        BeanUtils.copyProperties(roleDTO, role, "id");

        return role.toDTO();
    }

    public void deleteById(Long id) {
        this.roleRepository.deleteById(id);
    }

    private Role getRole(Long id) {
        final var roleDatabase = this.roleRepository.findById(id);

        if (roleDatabase.isEmpty())
            throw new EmptyResultDataAccessException("Role not found", 1);

        return roleDatabase.get();
    }
}
