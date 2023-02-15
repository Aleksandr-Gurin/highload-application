package authors.service.impl;

import authors.dto.request.AuthorRequest;
import authors.dto.response.AuthorResponse;
import authors.dto.update.AuthorUpdate;
import authors.exception.ObjectNotFoundException;
import authors.mapper.AuthorMapper;
import authors.repository.AuthorRepository;
import authors.service.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Flux<AuthorResponse> getAllPage(Pageable pageable) {
        return Flux.just(authorRepository.findAll(pageable))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Flux::fromIterable)
                .map(authorMapper::authorToAuthorResponse);
    }

    @Override
    public Flux<AuthorResponse> getAllList() {
        return Mono.fromCallable(authorRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .flux()
                .flatMap(Flux::fromIterable)
                .map(authorMapper::authorToAuthorResponse);
    }

    @Override
    public Mono<AuthorResponse> findById(UUID id) {
        return Mono.fromCallable(() -> authorRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .map(Optional::get)
                .map(authorMapper::authorToAuthorResponse)
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")));
    }

    @Override
    public Mono<Void> save(Mono<AuthorRequest> authorRequestMono) {
        return authorRequestMono.flatMap(audioRequest ->
                        Mono.fromCallable(() -> authorRepository.save(authorMapper.authorRequestToAuthor(audioRequest)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    public Mono<Void> update(Mono<AuthorUpdate> authorUpdateMono) {
        return authorUpdateMono.flatMap(audioUpdate ->
                        Mono.fromCallable(() -> authorRepository.save(authorMapper.authorUpdateToAuthor(audioUpdate)))
                                .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }

    @Override
    @Transactional
    public Mono<Void> deleteById(UUID id) {
        return Mono.fromCallable(() -> authorRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .switchIfEmpty(Mono.error(new ObjectNotFoundException("Audio with id " + id + " not found")))
                .then(Mono.fromRunnable(() -> authorRepository.deleteById(id))
                        .subscribeOn(Schedulers.boundedElastic()))
                .then();
    }
}
