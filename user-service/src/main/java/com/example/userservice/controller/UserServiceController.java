package com.example.userservice.controller;

import com.example.userservice.dto.UserFullResponseDTO;
import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RestController
@Slf4j
@RequestMapping("/inner")
public class UserServiceController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserResponseDTO> findById(@PathVariable UUID id) {
        return userService.findById(id).map(userMapper::toUserResponseDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Mono<UserFullResponseDTO> findByUsername(@RequestParam(value = "name") String username) {
        return userService.findByUsername(username).map(userMapper::toUserFullResponseDto);
    }
}
