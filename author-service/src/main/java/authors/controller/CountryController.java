package authors.controller;

import authors.dto.request.CountryRequest;
import authors.dto.response.CountryResponse;
import authors.dto.update.CountryUpdate;
import authors.service.CountryService;
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
@RequestMapping(value = "/api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @GetMapping
    public Flux<CountryResponse> getAllCountries(@RequestParam(defaultValue = "0")
                                                     @Min(value = 0, message = "must not be less than zero")
                                                     int page,
                                                 @RequestParam(defaultValue = "5")
                                                     @Max(value = 50, message = "must not be more than 50 characters")
                                                     int size) {
        return countryService.getAllPage(PageRequest.of(page, size));
    }

    @GetMapping("/all-country")
    public Flux<CountryResponse> getAllCountries() {
        return countryService.getAllList();
    }

    @GetMapping("/{id}")
    public Mono<CountryResponse> getCountryById(@PathVariable UUID id) {
        return countryService.findById(id);
    }

    @PostMapping
    public Mono<Void> addCountry(@Valid @RequestBody Mono<CountryRequest> countryRequest) {
        return countryService.save(countryRequest);
    }

    @PutMapping
    public Mono<Void> updateCountry(@Valid @RequestBody Mono<CountryUpdate> countryUpdate) {
        return countryService.update(countryUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCountry(@PathVariable UUID id) {
        return countryService.deleteById(id);
    }
}
