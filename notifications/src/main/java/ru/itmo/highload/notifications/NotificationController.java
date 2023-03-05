package ru.itmo.highload.notifications;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
public class NotificationController {
    private final RabbitService rabbitService;
    private final ObjectMapper objectMapper;

    @PostMapping("send")
    public Mono<Void> sendNotification(@RequestBody NotificationMessage message) throws JsonProcessingException {
        log.info("received {}", message);
        if(message.timestamp == null) message.timestamp = LocalDateTime.now();
        String msgString = objectMapper.writeValueAsString(message);
        return rabbitService.sendNotification(message.getUsername(), msgString);
    }
}
