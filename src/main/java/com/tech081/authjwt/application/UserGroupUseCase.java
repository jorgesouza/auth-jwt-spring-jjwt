package com.tech081.authjwt.application;

import com.tech081.authjwt.controller.dto.UserGroupDTO;
import com.tech081.authjwt.data.UserGroup;
import com.tech081.authjwt.data.UserGroupRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserGroupUseCase {

    private final UserGroupRepository userGroupRepository;

    public UserGroupUseCase(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public Optional<List<UserGroupDTO>> findAll() {
        final var userGroups = this.userGroupRepository.findAll();

        return Optional.of(userGroups
                .stream()
                .map(UserGroup::toDTO)
                .collect(Collectors.toList())
        );
    }

    public UserGroupDTO findById(Long id) {
        final UserGroup userGroup = this.getUserGroup(id);

        return userGroup.toDTO();
    }

    public UserGroupDTO save(UserGroupDTO userGroupDTO) {
        final var userGroup = userGroupDTO.toEntity();
        final var newUserGroup = this.userGroupRepository.save(userGroup);

        return newUserGroup.toDTO();
    }

    public UserGroupDTO update(Long id, UserGroupDTO userGroupDTO) {
        final UserGroup userGroup = this.getUserGroup(id);

        BeanUtils.copyProperties(userGroupDTO, userGroup, "id");

        return userGroup.toDTO();
    }

    public void deleteById(Long id) {
        this.userGroupRepository.deleteById(id);
    }

    private UserGroup getUserGroup(Long id) {
        final var userGroupDatabase = this.userGroupRepository.findById(id);

        if (userGroupDatabase.isEmpty())
            throw new EmptyResultDataAccessException("User group not found", 1);

        return userGroupDatabase.get();
    }
}
