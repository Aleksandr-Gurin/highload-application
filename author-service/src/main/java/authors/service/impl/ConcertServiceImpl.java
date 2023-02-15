package authors.service.impl;

import authors.dto.request.ConcertRequest;
import authors.dto.response.ConcertResponse;
import authors.dto.update.ConcertUpdate;
import authors.exception.ObjectNotFoundException;
import authors.mapper.ConcertMapper;
import authors.repository.ConcertRepository;
import authors.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConcertServiceImpl implements ConcertService {
    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;

    @Override
    public Flux<ConcertResponse> getAllPage(Pageable pageable) {
        return Flux.just(concertRepository.findAll(pageable))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Flux::fromIterable)
                .map(concertMapper::concertToConcertResponse);

    }

    @Override
    public Flux<ConcertResponse> getAllList() {
        return Mono.fromCallable(concertRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flux()
                .flatMap(Flux::fromIterable)
                .map(concertMapper::concertToConcertResponse);

    }

    @Override
    public Mono<ConcertResponse> findById(UUID id) {
        return Mono.fromCallable(() -> concertRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .map(concertMapper::concertToConcertResponse)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")));
    }

    @Override
    public Mono<Void> save(Mono<ConcertRequest> authorRequestMono) {
        return authorRequestMono.flatMap(audioRequest ->
                        Mono.fromCallable(() -> concertRepository.save(concertMapper.concertRequestToConcert(audioRequest)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<Void> update(Mono<ConcertUpdate> authorUpdateMono) {
        return authorUpdateMono.flatMap(audioUpdate ->
                        Mono.fromCallable(() -> concertRepository.save(concertMapper.concertUpdateToConcert(audioUpdate)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return Mono.fromCallable(() -> concertRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")))
                .then(Mono.fromRunnable(() -> concertRepository.deleteById(id))
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }
}
