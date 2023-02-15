package com.example.subscriptionservice.service.feign.clients;

import com.example.subscriptionservice.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

import java.util.UUID;

@FeignClient(name = "user-service")
public interface UserServiceFeignClient {
    @GetMapping("/inner/{id}")
    @ResponseStatus(HttpStatus.OK)
    UserResponseDTO findById(@PathVariable UUID id);
}
