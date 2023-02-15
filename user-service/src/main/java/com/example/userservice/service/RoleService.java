package com.example.userservice.service;

import com.example.userservice.controller.exception.EntityExistsException;
import com.example.userservice.controller.exception.NotFoundException;
import com.example.userservice.dto.RoleDTO;
import com.example.userservice.mapper.RoleMapper;
import com.example.userservice.model.Role;
import com.example.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class RoleService {
    private static final String EXC_MES_NAME = "role not found by name ";
    private static final String EXC_MES_ID = "role not found by id ";
    private static final String EXC_EXIST = "role with this name exists ";
    private final RoleRepository roleRepository;
    private final RoleMapper mapper;


    public Mono<Role> save(RoleDTO roleDTO) {
        return roleRepository.existsByName(roleDTO.getName())
                .flatMap(hasElement -> {
                    if (Boolean.TRUE.equals(hasElement))
                        return Mono.error(new EntityExistsException(EXC_EXIST));
                    return roleRepository.save(mapper.toRole(roleDTO));
                });
    }

    public Mono<Role> findByName(String name) {
        return roleRepository.findByName(name)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_NAME + name)));
    }

    public Mono<Role> findById(UUID id) {
        return roleRepository.findById(id)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_ID + id)));
    }

    public Mono<Page<Role>> findAll(Pageable pageable) {
        return roleRepository.findAllBy(pageable)
                .collectList()
                .zipWith(roleRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }

    public Mono<Void> deleteByName(String name) {
        return roleRepository.findByName(name)
                .switchIfEmpty(Mono.error(() -> new NotFoundException(EXC_MES_NAME + name)))
                .flatMap(roleRepository::delete);
    }
}
