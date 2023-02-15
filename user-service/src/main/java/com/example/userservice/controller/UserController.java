package com.example.userservice.controller;

import com.example.userservice.dto.PageDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.mapper.PageMapper;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final PageMapper<UserResponseDTO> pageMapper;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<UserResponseDTO> save(@Valid @RequestBody Mono<UserRequestDTO> userRequestDTOMono) {
        return userRequestDTOMono.flatMap(userRequestDTO ->
                userService.save(userRequestDTO)
                        .map(userMapper::toUserResponseDto)
        );
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponseDTO> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username).map(userMapper::toUserResponseDto);
    }

    @GetMapping
    public Mono<ResponseEntity<PageDTO<UserResponseDTO>>>
    findAll(@RequestParam(defaultValue = "0")
            @Min(value = 0, message = "must not be less than zero") int page,
            @RequestParam(defaultValue = "5")
            @Max(value = 50, message = "must not be more than 50 characters")
            @Min(value = 1, message = "must not be less than one") int size,
            @RequestParam(required = false) String role) {

        return userService.findAll(PageRequest.of(page, size), role)
                .map(pageUsers -> {
                    if (pageUsers.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                    return new ResponseEntity<>(
                            pageMapper.mapToDto(pageUsers.map(userMapper::toUserResponseDto)),
                            HttpStatus.OK
                    );
                });
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponseDTO> updateById(@RequestBody Map<String, String> updates, @PathVariable UUID id) {
        return userService.updateById(updates, id).map(userMapper::toUserResponseDto);
    }

    @DeleteMapping("/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String username) {
        return userService.deleteByUsername(username);
    }
}
