package com.example.reactiveauthendpointaggregator.controller;

import com.example.reactiveauthendpointaggregator.dto.author.request.AuthorRequest;
import com.example.reactiveauthendpointaggregator.dto.author.response.AuthorResponse;
import com.example.reactiveauthendpointaggregator.dto.author.update.AuthorUpdate;
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
@RequestMapping(value = "/api/v1/author")
public class AuthorController {

    private final AuthorServiceFeignClient authorClient;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Flux<AuthorResponse> getAllAuthors(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "5") int size) {
        return authorClient.getAllAuthors(page, size);
    }

    @GetMapping("/all-author")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Flux<AuthorResponse> getAllAuthors() {
        return authorClient.getAllAuthors();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public Mono<AuthorResponse> getAuthorById(@PathVariable UUID id) {
        return authorClient.getAuthorById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void> addAuthor(@RequestBody Mono<AuthorRequest> authorRequest) {
        return authorClient.addAuthor(authorRequest);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void>  updateAuthor(@RequestBody Mono<AuthorUpdate> authorUpdate) {
        return authorClient.updateAuthor(authorUpdate);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Mono<Void>  deleteAuthor(@PathVariable UUID id) {
        return authorClient.deleteAuthor(id);
    }
}
