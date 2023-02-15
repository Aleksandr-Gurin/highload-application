package authors.service.impl;

import authors.dto.request.CityRequest;
import authors.dto.response.CityResponse;
import authors.dto.update.CityUpdate;
import authors.exception.ObjectNotFoundException;
import authors.mapper.CityMapper;
import authors.repository.CityRepository;
import authors.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public Flux<CityResponse> getAllPage(Pageable pageable) {
        return Flux.just(cityRepository.findAll(pageable))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Flux::fromIterable)
                .map(cityMapper::cityToCityResponse);
    }

    @Override
    public Flux<CityResponse> getAllList() {
        return Mono.fromCallable(cityRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flux()
                .flatMap(Flux::fromIterable)
                .map(cityMapper::cityToCityResponse);
    }

    @Override
    public Mono<CityResponse> findById(UUID id) {
        return Mono.fromCallable(() -> cityRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .map(cityMapper::cityToCityResponse)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")));
    }

    @Override
    public Mono<Void> save(Mono<CityRequest> authorRequestMono) {
        return authorRequestMono.flatMap(audioRequest ->
                        Mono.fromCallable(() -> cityRepository.save(cityMapper.cityRequestToCity(audioRequest)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();

    }

    @Override
    public Mono<Void> update(Mono<CityUpdate> authorUpdateMono) {
        return authorUpdateMono.flatMap(audioUpdate ->
                        Mono.fromCallable(() -> cityRepository.save(cityMapper.cityUpdateToCity(audioUpdate)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();

    }

    @Override
    @Transactional
    public Mono<Void> deleteById(UUID id) {
        return Mono.fromCallable(() -> cityRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")))
                .then(Mono.fromRunnable(() -> cityRepository.deleteById(id))
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }
}
