package com.example.reactiveauthendpointaggregator.feignclient;

import com.example.reactiveauthendpointaggregator.dto.author.request.AudioRequest;
import com.example.reactiveauthendpointaggregator.dto.author.request.AuthorRequest;
import com.example.reactiveauthendpointaggregator.dto.author.request.CityRequest;
import com.example.reactiveauthendpointaggregator.dto.author.request.ConcertRequest;
import com.example.reactiveauthendpointaggregator.dto.author.request.CountryRequest;
import com.example.reactiveauthendpointaggregator.dto.author.response.AudioResponse;
import com.example.reactiveauthendpointaggregator.dto.author.response.AuthorResponse;
import com.example.reactiveauthendpointaggregator.dto.author.response.CityResponse;
import com.example.reactiveauthendpointaggregator.dto.author.response.ConcertResponse;
import com.example.reactiveauthendpointaggregator.dto.author.response.CountryResponse;
import com.example.reactiveauthendpointaggregator.dto.author.update.AudioUpdate;
import com.example.reactiveauthendpointaggregator.dto.author.update.AuthorUpdate;
import com.example.reactiveauthendpointaggregator.dto.author.update.CityUpdate;
import com.example.reactiveauthendpointaggregator.dto.author.update.ConcertUpdate;
import com.example.reactiveauthendpointaggregator.dto.author.update.CountryUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient("author-service")
public interface AuthorServiceFeignClient {

    @GetMapping("/api/v1/audio")
    Flux<AudioResponse> getAllAudios(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/audio/all-audio")
    Flux<AudioResponse> getAllAudios();

    @GetMapping("/api/v1/audio/{id}")
    Mono<AudioResponse> getAudioById(@PathVariable UUID id);

    @PostMapping("/api/v1/audio")
    Mono<Void> addAudio(@RequestBody Mono<AudioRequest> audioRequest);

    @PutMapping("/api/v1/audio")
    Mono<Void> updateAudio(@RequestBody Mono<AudioUpdate> audioUpdate);

    @DeleteMapping("/api/v1/audio/{id}")
    Mono<Void> deleteAudio(@PathVariable UUID id);

    @GetMapping("/api/v1/author")
    Flux<AuthorResponse> getAllAuthors(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/author/all-author")
    Flux<AuthorResponse> getAllAuthors();

    @GetMapping("/api/v1/author/{id}")
    Mono<AuthorResponse> getAuthorById(@PathVariable UUID id);

    @PostMapping("/api/v1/author")
    Mono<Void> addAuthor(@RequestBody Mono<AuthorRequest> authorRequest);

    @PutMapping("/api/v1/author")
    Mono<Void>  updateAuthor(@RequestBody Mono<AuthorUpdate> authorUpdate);

    @DeleteMapping("/api/v1/author/{id}")
    Mono<Void>  deleteAuthor(@PathVariable UUID id);

    @GetMapping("/api/v1/city")
    Flux<CityResponse> getAllCities(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/city/all-city")
    Flux<CityResponse> getAllCities();

    @GetMapping("/api/v1/city/{id}")
    Mono<CityResponse> getCityById(@PathVariable UUID id);

    @PostMapping("/api/v1/city")
    Mono<Void> addCity(@RequestBody Mono<CityRequest> cityRequest);

    @PutMapping("/api/v1/city")
    Mono<Void> updateCity(@RequestBody Mono<CityUpdate> cityUpdate);

    @DeleteMapping("/api/v1/city/{id}")
    Mono<Void> deleteCity(@PathVariable UUID id);

    @GetMapping("/api/v1/concert")
    Flux<ConcertResponse> getAllConcerts(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/concert/all-concert")
    Flux<ConcertResponse> getAllConcerts();

    @GetMapping("/api/v1/concert/{id}")
    Mono<ConcertResponse> getConcertById(@PathVariable UUID id);

    @PostMapping("/api/v1/concert")
    Mono<Void> addConcert(@RequestBody Mono<ConcertRequest> concertRequest);

    @PutMapping("/api/v1/concert")
    Mono<Void> updateConcert(@RequestBody Mono<ConcertUpdate> concertUpdate);

    @DeleteMapping("/api/v1/concert/{id}")
    Mono<Void> deleteConcert(@PathVariable UUID id);

    @GetMapping("/api/v1/country")
    Flux<CountryResponse> getAllCountries(@PageableDefault(size = 5) Pageable pageable);

    @GetMapping("/api/v1/country/all-country")
    Flux<CountryResponse> getAllCountries();

    @GetMapping("/api/v1/country/{id}")
    Mono<CountryResponse> getCountryById(@PathVariable UUID id);

    @PostMapping("/api/v1/country")
    Mono<Void> addCountry(@RequestBody Mono<CountryRequest> countryRequest);

    @PutMapping("/api/v1/country")
    Mono<Void> updateCountry(@RequestBody Mono<CountryUpdate> countryUpdate);

    @DeleteMapping("/api/v1/country/{id}")
    Mono<Void> deleteCountry(@PathVariable UUID id);
}
