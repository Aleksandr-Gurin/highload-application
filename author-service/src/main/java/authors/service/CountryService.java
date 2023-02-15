package authors.service;

import authors.dto.request.CountryRequest;
import authors.dto.response.CountryResponse;
import authors.dto.update.CountryUpdate;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CountryService {
    Flux<CountryResponse> getAllPage(Pageable pageable);

    Flux<CountryResponse> getAllList();

    Mono<CountryResponse> findById(UUID id);

    Mono<Void> save(Mono<CountryRequest> countryRequestMono);

    Mono<Void> update(Mono<CountryUpdate> countryUpdateMono);

    Mono<Void> deleteById(UUID id);
}
