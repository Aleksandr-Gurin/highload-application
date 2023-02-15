package authors.service.impl;

import authors.dto.request.AudioRequest;
import authors.dto.response.AudioResponse;
import authors.dto.update.AudioUpdate;
import authors.exception.ObjectNotFoundException;
import authors.mapper.AudioMapper;
import authors.repository.AudioRepository;
import authors.service.AudioService;
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
public class AudioServiceImpl implements AudioService {
    private final AudioRepository audioRepository;
    private final AudioMapper audioMapper;

    @Override
    public Flux<AudioResponse> getAllPage(Pageable pageable) {
        return Flux.just(audioRepository.findAll(pageable))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Flux::fromIterable)
                .map(audioMapper::audioToAudioResponse);
    }

    @Override
    public Flux<AudioResponse> getAllList() {
        return Mono.fromCallable(audioRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flux()
                .flatMap(Flux::fromIterable)
                .map(audioMapper::audioToAudioResponse);
    }

    @Override
    public Mono<AudioResponse> findById(UUID id) {
        return Mono.fromCallable(() -> audioRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .map(audioMapper::audioToAudioResponse)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")));
    }

    @Override
    public Mono<Void> save(Mono<AudioRequest> monoAudioRequest) {
        return monoAudioRequest.flatMap(audioRequest ->
                        Mono.fromCallable(() -> audioRepository.save(audioMapper.audioRequestToAudio(audioRequest)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<Void> update(Mono<AudioUpdate> monoAudioUpdate) {
        return monoAudioUpdate.flatMap(audioUpdate ->
                        Mono.fromCallable(() -> audioRepository.save(audioMapper.audioUpdateToAudio(audioUpdate)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<Void> disableById(UUID id) {
        return Mono.fromCallable(() -> audioRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")))
                .then(Mono.fromRunnable(() -> audioRepository.deleteById(id))
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }
}
