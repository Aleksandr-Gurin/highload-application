package com.example.reactiveauthendpointaggregator.controller;

import com.example.reactiveauthendpointaggregator.dto.author.request.AudioRequest;
import com.example.reactiveauthendpointaggregator.dto.author.response.AudioResponse;
import com.example.reactiveauthendpointaggregator.dto.author.update.AudioUpdate;
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
@PreAuthorize("hasAnyAuthority('ADMIN','USER')")
@RequestMapping(value = "/api/v1/audio")
public class AudioController {

    private final AuthorServiceFeignClient authorClient;

    @GetMapping
    public Flux<AudioResponse> getAllAudios(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "5") int size) {
        return authorClient.getAllAudios(page, size);
    }

    @GetMapping("/all-audio")
    public Flux<AudioResponse> getAllAudios() {
        return authorClient.getAllAudios();
    }

    @GetMapping("/{id}")
    public Mono<AudioResponse> getAudioById(@PathVariable UUID id) {
        return authorClient.getAudioById(id);
    }

    @PostMapping
    public Mono<Void> addAudio(@RequestBody Mono<AudioRequest> audioRequest) {
        return authorClient.addAudio(audioRequest);
    }

    @PutMapping
    public Mono<Void> updateAudio(@RequestBody Mono<AudioUpdate> audioUpdate) {
        return authorClient.updateAudio(audioUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAudio(@PathVariable UUID id) {
        return authorClient.deleteAudio(id);
    }
}
