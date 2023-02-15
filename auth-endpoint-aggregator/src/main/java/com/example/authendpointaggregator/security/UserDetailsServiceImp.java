package com.example.authendpointaggregator.security;

import com.example.authendpointaggregator.dto.UserFullResponseDTO;
import com.example.authendpointaggregator.feignclient.UserServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserServiceFeignClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserFullResponseDTO user = userClient.findByUsername(username);
        return User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().getName())
                .build();
    }
}
