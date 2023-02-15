package com.example.userservice.mapper;

import com.example.userservice.dto.RoleDTO;
import com.example.userservice.model.Role;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleMapper {

    public RoleDTO toRoleDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public Role toRole(RoleDTO roleDTO) {
        return Role.builder()
                .id(UUID.randomUUID())
                .name(roleDTO.getName())
                .newRole(true)
                .build();
    }
}
