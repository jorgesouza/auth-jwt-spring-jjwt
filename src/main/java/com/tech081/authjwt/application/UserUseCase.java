package com.tech081.authjwt.application;

import com.tech081.authjwt.controller.dto.UserDTO;
import com.tech081.authjwt.data.User;
import com.tech081.authjwt.data.UserGroupRepository;
import com.tech081.authjwt.data.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserUseCase {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;

    public UserUseCase(UserRepository userRepository, UserGroupRepository userGroupRepository) {
        this.userRepository = userRepository;
        this.userGroupRepository = userGroupRepository;
    }

    public Optional<List<UserDTO>> findAll() {
        final var users = this.userRepository.findAll();

        return Optional.of(users
                .stream()
                .map(User::toDTO)
                .collect(Collectors.toList())
        );
    }

    public UserDTO findById(Long id) {
        final User user = this.getUser(id);

        return user.toDTO();
    }

    public UserDTO save(UserDTO userDTO) {
        final var userGroup = this.userGroupRepository.findByDescription(userDTO.getUserGroup().getDescription());
        final var user = userDTO.toEntity();
        userGroup.ifPresent(user::setUserGroup);

        final var newUser = this.userRepository.save(user);

        return newUser.toDTO();
    }

    public UserDTO update(Long id, UserDTO userDTO) {
        final User user = this.getUser(id);

        BeanUtils.copyProperties(userDTO, user, "id", "createdAt");

        return user.toDTO();
    }

    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    private User getUser(Long id) {
        final var userDatabase = this.userRepository.findById(id);

        if (userDatabase.isEmpty())
            throw new EmptyResultDataAccessException("User not found", 1);

        return userDatabase.get();
    }
}
