package authors.service.impl;

import authors.dto.request.CountryRequest;
import authors.dto.response.CountryResponse;
import authors.dto.update.CountryUpdate;
import authors.exception.ObjectNotFoundException;
import authors.mapper.CountryMapper;
import authors.model.City;
import authors.repository.CityRepository;
import authors.repository.CountryRepository;
import authors.service.CityService;
import authors.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final CityService cityService;
    private final CityRepository cityRepository;
    private final CountryMapper countryMapper;

    @Override
    public Flux<CountryResponse> getAllPage(Pageable pageable) {
        return Flux.just(countryRepository.findAll(pageable))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Flux::fromIterable)
                .map(countryMapper::countryToCountryResponse);
    }

    @Override
    public Flux<CountryResponse> getAllList() {
        return Mono.fromCallable(countryRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flux()
                .flatMap(Flux::fromIterable)
                .map(countryMapper::countryToCountryResponse);

    }

    @Override
    public Mono<CountryResponse> findById(UUID id) {
        return Mono.fromCallable(() -> countryRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .map(countryMapper::countryToCountryResponse)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")));
    }

    @Override
    public Mono<Void> save(Mono<CountryRequest> countryRequestMono) {
        return countryRequestMono.flatMap(audioRequest ->
                        Mono.fromCallable(() -> countryRepository.save(countryMapper.countryRequestToCountry(audioRequest)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<Void> update(Mono<CountryUpdate> countryUpdateMono) {
        return countryUpdateMono.flatMap(audioUpdate ->
                        Mono.fromCallable(() -> countryRepository.save(countryMapper.countryUpdateToCountry(audioUpdate)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(UUID id) {
        return Mono.fromCallable(() -> countryRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")))
                .then(Mono.fromRunnable(() -> {
                            cityRepository.deleteAllByCountryId(id);
                            countryRepository.deleteById(id);
                        })
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();

    }
}
