package ru.itmo.highload.storroom.files.clients;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Mono;
import ru.itmo.highload.storroom.files.dtos.NotificationMessage;

@ReactiveFeignClient(name = "NOTIFICATIONS", url = "http://NOTIFICATIONS", fallback = NotificationClientFallback.class)
public interface NotificationClient {

    @PostMapping("/send")
    Mono<Void> sendNotification(@RequestBody NotificationMessage message);
}
