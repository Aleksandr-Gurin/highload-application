package authors.service;

import authors.dto.request.AuthorRequest;
import authors.dto.response.AuthorResponse;
import authors.dto.update.AuthorUpdate;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthorService {
    Flux<AuthorResponse> getAllPage(Pageable pageable);

    Flux<AuthorResponse> getAllList();

    Mono<AuthorResponse> findById(UUID id);

    Mono<Void> save(Mono<AuthorRequest> authorRequestMono);

    Mono<Void> update(Mono<AuthorUpdate> authorUpdateMono);

    Mono<Void> deleteById(UUID id);
}
