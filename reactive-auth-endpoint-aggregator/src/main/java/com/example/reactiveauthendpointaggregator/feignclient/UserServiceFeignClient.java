package com.example.reactiveauthendpointaggregator.feignclient;

import com.example.reactiveauthendpointaggregator.dto.user.PageDTO;
import com.example.reactiveauthendpointaggregator.dto.user.RoleDTO;
import com.example.reactiveauthendpointaggregator.dto.user.UserFullResponseDTO;
import com.example.reactiveauthendpointaggregator.dto.user.UserRequestDTO;
import com.example.reactiveauthendpointaggregator.dto.user.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@ReactiveFeignClient(name = "user-service")
public interface UserServiceFeignClient {

    @GetMapping("/inner")
    Mono<UserFullResponseDTO> findByUsernameWithPassword(@RequestParam(value = "name") String username);

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<UserResponseDTO> saveUser(@RequestBody Mono<UserRequestDTO> userRequestDTOMono);

    @GetMapping("/users/{username}")
    Mono<UserResponseDTO> findUserByUsername(@PathVariable String username);

    @GetMapping("/users")
    Mono<PageDTO<UserResponseDTO>> findAllUsers(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size,
                                                @RequestParam(required = false) String role);

    @PatchMapping("/users/{id}")
    Mono<UserResponseDTO> updateUserById(@RequestBody Map<String, String> updates, @PathVariable UUID id);

    @DeleteMapping("/users/{username}")
    Mono<Void> deleteUser(@PathVariable String username);

    @PostMapping("/roles")
    Mono<RoleDTO> saveRole(@RequestBody Mono<RoleDTO> roleDTOMono);


    @GetMapping("/roles/{name}")
    Mono<RoleDTO> findRoleByName(@PathVariable String name);

    @GetMapping("/roles")
    Mono<PageDTO<RoleDTO>> findAllRoles(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "5") int size);

    @DeleteMapping("/roles/{name}")
    Mono<Void> deleteRole(@PathVariable String name);
}
