package com.example.userservice.security;

import com.example.userservice.controller.exception.BadCredentialsException;
import com.example.userservice.controller.exception.NotFoundException;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthResponseDTO> login(@Valid @RequestBody Mono<AuthRequestDTO> authRequestDTOMono) {
        return authRequestDTOMono.flatMap(authRequestDTO ->
                userService.findByUsername(authRequestDTO.getUsername())
                        .flatMap(user -> {
                            if (!passwordEncoder.matches(authRequestDTO.getPassword(), user.getPassword()))
                                return Mono.error(new BadCredentialsException());
                            return Mono.just(new AuthResponseDTO(
                                    jwtTokenProvider.createToken(user.getUsername(), user.getRoleName())
                            ));
                        })
                        .doOnError(throwable -> {
                            if (throwable instanceof NotFoundException)
                                throw new BadCredentialsException();
                        })
        );
    }
}
