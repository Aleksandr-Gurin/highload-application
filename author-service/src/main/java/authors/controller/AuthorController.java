package authors.controller;

import authors.dto.request.AuthorRequest;
import authors.dto.response.AuthorResponse;
import authors.dto.update.AuthorUpdate;
import authors.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/author")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public Flux<AuthorResponse> getAllAuthors(@RequestParam(defaultValue = "0")
                                                  @Min(value = 0, message = "must not be less than zero")
                                                  int page,
                                              @RequestParam(defaultValue = "5")
                                                  @Max(value = 50, message = "must not be more than 50 characters")
                                                  int size) {
        return authorService.getAllPage(PageRequest.of(page, size));
    }

    @GetMapping("/all-author")
    public Flux<AuthorResponse> getAllAuthors() {
        return authorService.getAllList();
    }

    @GetMapping("/{id}")
    public Mono<AuthorResponse> getAuthorById(@PathVariable UUID id) {
        return authorService.findById(id);
    }

    @PostMapping
    public Mono<Void> addAuthor(@Valid @RequestBody Mono<AuthorRequest> authorRequest) {
        return authorService.save(authorRequest);
    }

    @PutMapping
    public Mono<Void>  updateAuthor(@Valid @RequestBody Mono<AuthorUpdate> authorUpdate) {
        return authorService.update(authorUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void>  deleteAuthor(@PathVariable UUID id) {
        return authorService.deleteById(id);
    }
}
