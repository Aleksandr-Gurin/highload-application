package authors.service;

import authors.dto.request.AudioRequest;
import authors.dto.response.AudioResponse;
import authors.dto.update.AudioUpdate;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AudioService {
    Flux<AudioResponse> getAllPage(Pageable pageable);

    Flux<AudioResponse> getAllList();

    Mono<AudioResponse> findById(UUID id);

    Mono<Void> save(Mono<AudioRequest> monoAudioRequest);

    Mono<Void> update(Mono<AudioUpdate> monoAudioUpdate);

    Mono<Void> disableById(UUID id);
}
