package com.example.userservice.service;

import com.example.userservice.controller.exception.EntityExistsException;
import com.example.userservice.controller.exception.NotFoundException;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserDatabaseClient;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private static final String EXC_MES_USERNAME = "user not found by username ";
    private static final String EXC_MES_ID = "user not found by id ";
    private static final String EXC_EXIST = "user with this username exists ";
    private final UserRepository userRepository;
    private final UserDatabaseClient userDatabaseClient;

    private final RoleService roleService;
    private final UserMapper mapper;

    public Mono<User> save(UserRequestDTO userRequestDTO) {
        return userRepository.existsByUsername(userRequestDTO.getUsername())
                .flatMap(hasElement -> {
                    if (Boolean.TRUE.equals(hasElement))
                        return Mono.error(new EntityExistsException(EXC_EXIST));
                    return roleService.findByName(userRequestDTO.getRole());
                })
                .flatMap(role -> userRepository.save(mapper.dtoToUser(userRequestDTO, role.getId()))
                        .map(saveUser -> {
                                    saveUser.setRoleName(role.getName());
                                    return saveUser;
                                }
                        )
                );
    }

    public Mono<User> findByUsername(String username) {
        return userDatabaseClient.findByUsername(username)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_USERNAME + username)));
    }

    public Mono<User> findById(UUID id) {
        return userDatabaseClient.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_ID + id)));
    }

    public Mono<Page<User>> findAll(Pageable pageable, String roleName) {
        if (roleName != null) {
            return createPage(userDatabaseClient.findAllByRole(roleName, pageable), pageable);
        }
        return createPage(userDatabaseClient.findAll(pageable), pageable);
    }

    private Mono<Page<User>> createPage(Flux<User> userFlux, Pageable pageable) {
        return userFlux
                .collectList()
                .zipWith(userRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }

    public Mono<User> updateById(Map<String, String> updates, UUID id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_ID + id)))
                .flatMap(user -> userRepository.save(mapper.update(updates, user)))
                .flatMap(this::getUserWithRole);
    }

    public Mono<Void> deleteByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_USERNAME + username)))
                .flatMap(userRepository::delete);
    }

    private Mono<User> getUserWithRole(User user) {
        return roleService.findById(user.getRoleId())
                .map(role -> {
                    user.setRoleName(role.getName());
                    return user;
                });
    }
}
