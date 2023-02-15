package authors.service;

import authors.dto.request.ConcertRequest;
import authors.dto.response.ConcertResponse;
import authors.dto.update.ConcertUpdate;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ConcertService {
    Flux<ConcertResponse> getAllPage(Pageable pageable);

    Flux<ConcertResponse> getAllList();

    Mono<ConcertResponse> findById(UUID id);

    Mono<Void> save(Mono<ConcertRequest> authorRequestMono);

    Mono<Void> update(Mono<ConcertUpdate> authorUpdateMono);

    Mono<Void> deleteById(UUID id);
}
