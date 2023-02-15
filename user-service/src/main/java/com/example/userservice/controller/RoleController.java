package com.example.userservice.controller;

import com.example.userservice.dto.PageDTO;
import com.example.userservice.dto.RoleDTO;
import com.example.userservice.mapper.PageMapper;
import com.example.userservice.mapper.RoleMapper;
import com.example.userservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;
    private final PageMapper<RoleDTO> pageMapper;
    private final RoleMapper roleMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<RoleDTO> save(@Valid @RequestBody Mono<RoleDTO> roleDTOMono) {
        return roleDTOMono.flatMap(roleDTO ->
                roleService.save(roleDTO)
                        .map(roleMapper::toRoleDTO)
        );
    }


    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<RoleDTO> findByName(@PathVariable String name) {
        return roleService.findByName(name).map(roleMapper::toRoleDTO);
    }

    @GetMapping
    public Mono<ResponseEntity<PageDTO<RoleDTO>>>
    findAll(@RequestParam(defaultValue = "0")
            @Min(value = 0, message = "must not be less than zero") int page,
            @RequestParam(defaultValue = "5")
            @Max(value = 50, message = "must not be more than 50 characters")
            @Min(value = 1, message = "must not be less than one") int size) {

        return roleService.findAll(PageRequest.of(page, size))
                .map(pageRoles -> {
                    if (pageRoles.isEmpty())
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    return
                            new ResponseEntity<>(
                                    pageMapper.mapToDto(pageRoles.map(roleMapper::toRoleDTO)),
                                    HttpStatus.OK
                            );
                });
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String name) {
        return roleService.deleteByName(name);
    }

}
