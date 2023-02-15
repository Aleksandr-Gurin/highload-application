package authors.controller;

import authors.dto.request.ConcertRequest;
import authors.dto.response.ConcertResponse;
import authors.dto.update.ConcertUpdate;
import authors.service.ConcertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/concert")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping
    public Flux<ConcertResponse> getAllConcerts(@PageableDefault(size = 5) Pageable pageable) {
        return concertService.getAllPage(pageable);
    }

    @GetMapping("/all-concert")
    public Flux<ConcertResponse> getAllConcerts() {
        return concertService.getAllList();
    }

    @GetMapping("/{id}")
    public Mono<ConcertResponse> getConcertById(@PathVariable UUID id) {
        return concertService.findById(id);
    }

    @PostMapping
    public Mono<Void> addConcert(@Valid @RequestBody Mono<ConcertRequest> concertRequest) {
        return concertService.save(concertRequest);
    }

    @PutMapping
    public Mono<Void> updateConcert(@Valid @RequestBody Mono<ConcertUpdate> concertUpdate) {
        return concertService.update(concertUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteConcert(@PathVariable UUID id) {
        return concertService.deleteById(id);
    }
}
