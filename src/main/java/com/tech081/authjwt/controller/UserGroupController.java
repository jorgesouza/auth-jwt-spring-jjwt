package com.tech081.authjwt.controller;

import com.tech081.authjwt.application.UserGroupUseCase;
import com.tech081.authjwt.controller.dto.UserGroupDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/user-group")
public class UserGroupController {

    private final UserGroupUseCase userGroupUseCase;

    public UserGroupController(UserGroupUseCase userGroupUseCase) {
        this.userGroupUseCase = userGroupUseCase;
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<List<UserGroupDTO>> findAll() {
        final var userGroup = userGroupUseCase.findAll();

        return userGroup.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserGroupDTO> findById(@PathVariable Long id) {
        final var userGroup = userGroupUseCase.findById(id);

        return ResponseEntity.ok(userGroup);
    }

    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserGroupDTO> save(@RequestBody @Valid UserGroupDTO userGroupDTO, UriComponentsBuilder uriBuilder) {
        final var newUserGroup = userGroupUseCase.save(userGroupDTO);
        final var uri = uriBuilder.path("/user-group/{id}").buildAndExpand(newUserGroup.getId()).toUri();

        return ResponseEntity.created(uri).body(newUserGroup);
    }

    @PutMapping("/{id}")
    @Transactional
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<UserGroupDTO> update(@PathVariable Long id, @RequestBody @Valid UserGroupDTO userGroupDTO) {
        final var updatedUserGroup = userGroupUseCase.update(id, userGroupDTO);
        return ResponseEntity.ok(updatedUserGroup);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        userGroupUseCase.deleteById(id);
    }
}
