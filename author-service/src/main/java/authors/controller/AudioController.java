package authors.controller;

import authors.dto.request.AudioRequest;
import authors.dto.response.AudioResponse;
import authors.dto.update.AudioUpdate;
import authors.service.AudioService;
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
@RequestMapping(value = "/api/v1/audio")
public class AudioController {

    private final AudioService audioService;

    @GetMapping
    public Flux<AudioResponse> getAllAudios(@PageableDefault(size = 5) Pageable pageable) {
        return audioService.getAllPage(pageable);
    }

    @GetMapping("/all-audio")
    public Flux<AudioResponse> getAllAudios() {
        return audioService.getAllList();
    }

    @GetMapping("/{id}")
    public Mono<AudioResponse> getAudioById(@PathVariable UUID id) {
        return audioService.findById(id);
    }

    @PostMapping
    public Mono<Void> addAudio(@Valid @RequestBody Mono<AudioRequest> audioRequest) {
        return audioService.save(audioRequest);
    }

    @PutMapping
    public Mono<Void> updateAudio(@Valid @RequestBody Mono<AudioUpdate> audioUpdate) {
        return audioService.update(audioUpdate);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAudio(@PathVariable UUID id) {
        return audioService.disableById(id);
    }
}
