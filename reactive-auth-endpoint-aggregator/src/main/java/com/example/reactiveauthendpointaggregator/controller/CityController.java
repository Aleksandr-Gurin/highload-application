package com.example.reactiveauthendpointaggregator.controller;

import com.example.reactiveauthendpointaggregator.dto.author.request.CityRequest;
import com.example.reactiveauthendpointaggregator.dto.author.response.CityResponse;
import com.example.reactiveauthendpointaggregator.dto.author.update.CityUpdate;
import com.example.reactiveauthendpointaggregator.feignclient.AuthorServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/city")
public class CityController {

    private final AuthorServiceFeignClient authorClient;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Flux<CityResponse> getAllCities(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        return authorClient.getAllCities(page, size);
    }

    @GetMapping("/all-city")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Flux<CityResponse> getAllCities() {
        return authorClient.getAllCities();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Mono<CityResponse> getCityById(@PathVariable UUID id) {
        return authorClient.getCityById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void> addCity(@RequestBody Mono<CityRequest> cityRequest) {
        return authorClient.addCity(cityRequest);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void> updateCity(@RequestBody Mono<CityUpdate> cityUpdate) {
        return authorClient.updateCity(cityUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void> deleteCity(@PathVariable UUID id) {
        return authorClient.deleteCity(id);
    }
}
