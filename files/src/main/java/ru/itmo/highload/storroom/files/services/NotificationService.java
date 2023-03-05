package ru.itmo.highload.storroom.files.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import ru.itmo.highload.storroom.files.clients.NotificationClient;
import ru.itmo.highload.storroom.files.dtos.NotificationMessage;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    final NotificationClient client;

    public Mono<Void> sendNotification(@RequestBody NotificationMessage message) {
        return client.sendNotification(message);
    }
}
