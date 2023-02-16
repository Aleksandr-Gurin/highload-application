package com.example.reactiveauthendpointaggregator.controller;

import com.example.reactiveauthendpointaggregator.dto.author.request.CountryRequest;
import com.example.reactiveauthendpointaggregator.dto.author.response.CountryResponse;
import com.example.reactiveauthendpointaggregator.dto.author.update.CountryUpdate;
import com.example.reactiveauthendpointaggregator.feignclient.AuthorServiceFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
@RequestMapping(value = "/api/v1/country")
public class CountryController {

    private final AuthorServiceFeignClient authorClient;

    @GetMapping
    public Flux<CountryResponse> getAllCountries(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int size) {
        return authorClient.getAllCountries(page, size);
    }

    @GetMapping("/all-country")
    public Flux<CountryResponse> getAllCountries() {
        return authorClient.getAllCountries();
    }

    @GetMapping("/{id}")
    public Mono<CountryResponse> getCountryById(@PathVariable UUID id) {
        return authorClient.getCountryById(id);
    }

    @PostMapping
    public Mono<Void> addCountry(@RequestBody Mono<CountryRequest> countryRequest) {
        return authorClient.addCountry(countryRequest);
    }

    @PutMapping
    public Mono<Void> updateCountry(@RequestBody Mono<CountryUpdate> countryUpdate) {
        return authorClient.updateCountry(countryUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCountry(@PathVariable UUID id) {
        return authorClient.deleteCountry(id);
    }
}
