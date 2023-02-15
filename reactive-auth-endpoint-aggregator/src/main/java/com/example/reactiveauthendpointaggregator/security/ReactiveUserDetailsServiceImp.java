package com.example.reactiveauthendpointaggregator.security;

import com.example.reactiveauthendpointaggregator.feignclient.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
@Slf4j
@Service
public class ReactiveUserDetailsServiceImp implements ReactiveUserDetailsService {
    private final UserServiceFeignClient userClient;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userClient.findByUsernameWithPassword(username)
                .map(user -> User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole().getName())
                        .build());
    }
}

