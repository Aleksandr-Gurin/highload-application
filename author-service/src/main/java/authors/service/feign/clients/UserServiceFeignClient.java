package authors.service.feign.clients;

import authors.dto.response.UserResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ReactiveFeignClient(name = "user-service")
public interface UserServiceFeignClient {
    @GetMapping("/inner/{id}")
    @ResponseStatus(HttpStatus.OK)
    Mono<UserResponseDTO> findById(@PathVariable UUID id);
}
