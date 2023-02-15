package authors.controller;

import authors.dto.request.CityRequest;
import authors.dto.response.CityResponse;
import authors.dto.update.CityUpdate;
import authors.service.CityService;
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
@RequestMapping(value = "/api/v1/city")
public class CityController {

    private final CityService cityService;

    @GetMapping
    public Flux<CityResponse> getAllCities(@PageableDefault(size = 5) Pageable pageable) {
        return cityService.getAllPage(pageable);
    }

    @GetMapping("/all-city")
    public Flux<CityResponse> getAllCities() {
        return cityService.getAllList();
    }

    @GetMapping("/{id}")
    public Mono<CityResponse> getCityById(@PathVariable UUID id) {
        return cityService.findById(id);
    }

    @PostMapping
    public Mono<Void> addCity(@Valid @RequestBody Mono<CityRequest> cityRequest) {
        return cityService.save(cityRequest);
    }

    @PutMapping
    public Mono<Void> updateCity(@Valid @RequestBody Mono<CityUpdate> cityUpdate) {
        return cityService.update(cityUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteCity(@PathVariable UUID id) {
        return cityService.deleteById(id);
    }
}
