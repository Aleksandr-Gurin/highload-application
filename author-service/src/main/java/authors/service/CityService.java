package authors.service;

import authors.dto.request.CityRequest;
import authors.dto.response.CityResponse;
import authors.dto.update.CityUpdate;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface CityService {
    Flux<CityResponse> getAllPage(Pageable pageable);

    Flux<CityResponse> getAllList();

    Mono<CityResponse> findById(UUID id);

    Mono<Void> save(Mono<CityRequest> authorRequestMono);

    Mono<Void> update(Mono<CityUpdate> authorUpdateMono);

    Mono<Void> deleteById(UUID id);
}
