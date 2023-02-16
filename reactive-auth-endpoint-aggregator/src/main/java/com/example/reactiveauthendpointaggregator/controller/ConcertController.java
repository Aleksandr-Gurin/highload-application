package com.example.reactiveauthendpointaggregator.controller;

import com.example.reactiveauthendpointaggregator.dto.author.request.ConcertRequest;
import com.example.reactiveauthendpointaggregator.dto.author.response.ConcertResponse;
import com.example.reactiveauthendpointaggregator.dto.author.update.ConcertUpdate;
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
@RequestMapping(value = "/api/v1/concert")
public class ConcertController {

    private final AuthorServiceFeignClient authorClient;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Flux<ConcertResponse> getAllConcerts(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int size) {
        return authorClient.getAllConcerts(page, size);
    }

    @GetMapping("/all-concert")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Flux<ConcertResponse> getAllConcerts() {
        return authorClient.getAllConcerts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Mono<ConcertResponse> getConcertById(@PathVariable UUID id) {
        return authorClient.getConcertById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Mono<Void> addConcert(@RequestBody Mono<ConcertRequest> concertRequest) {
        return authorClient.addConcert(concertRequest);
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Mono<Void> updateConcert(@RequestBody Mono<ConcertUpdate> concertUpdate) {
        return authorClient.updateConcert(concertUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Mono<Void> deleteConcert(@PathVariable UUID id) {
        return authorClient.deleteConcert(id);
    }
}
