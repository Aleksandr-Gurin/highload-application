package ru.itmo.highload.storroom.files.clients;

import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ru.itmo.highload.storroom.files.dtos.NotificationMessage;

public class NotificationClientFallback implements NotificationClient {
    public Mono<Void> sendNotification(@RequestBody NotificationMessage message) {
        return Mono.empty();
    }
}
