package com.tech081.authjwt.controller;

import com.tech081.authjwt.application.RolesUseCase;
import com.tech081.authjwt.controller.dto.RoleDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/roles")
public class RoleController {

    private final RolesUseCase rolesUseCase;

    public RoleController(RolesUseCase rolesUseCase) {
        this.rolesUseCase = rolesUseCase;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<RoleDTO>> findAll() {
        final var roles = rolesUseCase.findAll();

        return roles.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<RoleDTO> findById(@PathVariable Long id) {
        final var role = rolesUseCase.findById(id);

        return ResponseEntity.ok(role);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<RoleDTO> save(@RequestBody @Valid RoleDTO roleDTO, UriComponentsBuilder uriBuilder) {
        final var newRole = rolesUseCase.save(roleDTO);
        final var uri = uriBuilder.path("/roles/{id}").buildAndExpand(newRole.getId()).toUri();

        return ResponseEntity.created(uri).body(newRole);
    }

    @PutMapping("/{id}")
    @Transactional
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody @Valid RoleDTO roleDTO) {
        final var updatedRole = rolesUseCase.update(id, roleDTO);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        rolesUseCase.deleteById(id);
    }
}
