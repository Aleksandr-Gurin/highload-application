package com.example.subscriptionservice.service.feign.clients;

import com.example.subscriptionservice.dto.UserResponseDTO;
import com.example.subscriptionservice.dto.response.ConcertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

import java.util.UUID;

@FeignClient(name = "author-service")
public interface AuthorServiceFeignClient {
    @GetMapping("/api/v1/concert/{id}")
    @ResponseStatus(HttpStatus.OK)
    ConcertResponse findConcertById(@PathVariable UUID id);
}
