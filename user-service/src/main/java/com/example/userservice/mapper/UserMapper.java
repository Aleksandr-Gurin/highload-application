package com.example.userservice.mapper;

import com.example.userservice.dto.RoleDTO;
import com.example.userservice.dto.UserFullResponseDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.model.User;
import com.example.userservice.service.ValidatorModel;
import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;
    private final ValidatorModel validator;

    public UserResponseDTO toUserResponseDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(RoleDTO.builder()
                        .id(user.getRoleId())
                        .name(user.getRoleName())
                        .build())
                .build();
    }

    public UserFullResponseDTO toUserFullResponseDto(User user) {
        return UserFullResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(RoleDTO.builder()
                        .id(user.getRoleId())
                        .name(user.getRoleName())
                        .build())
                .build();
    }

    public User dtoToUser(UserRequestDTO userRequestDTO, UUID roleId) {
        return User.builder()
                .id(UUID.randomUUID())
                .username(userRequestDTO.getUsername())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .roleId(roleId)
                .newUser(true)
                .build();
    }

    public User rowToUser(Row row) {
        return User.builder()
                .id(row.get("id", UUID.class))
                .username(row.get("username", String.class))
                .password(row.get("password", String.class))
                .roleId(row.get("role_id", UUID.class))
                .roleName(row.get("name", String.class))
                .build();
    }

    public User update(Map<String, String> updates, User user) {
        updates.forEach((key, value) -> {
            switch (key) {
                case "role" -> user.setRoleId(UUID.fromString(value));
                case "password" -> user.setPassword(passwordEncoder.encode(value));
                case "username" -> user.setUsername(value);
                default -> throw new IllegalArgumentException("no such attribute " + key);
            }
        });
        validator.validate(user);
        return user;
    }


}
