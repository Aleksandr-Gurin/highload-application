package ru.itmo.highload.stroroom.notificationsws.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.Sender;
import ru.itmo.highload.stroroom.notificationsws.dtos.NotificationMessage;


@Service
@RequiredArgsConstructor
@Slf4j
public class RabbitService {
    final ObjectMapper objectMapper;
    final Sender sender;
    final Receiver receiver;

    public Flux<String> listenQueue(String queueName) {
        return sender.declareQueue(QueueSpecification.queue(queueName).autoDelete(true))
                .onErrorMap(i -> {
                    i.printStackTrace();
                    return i;
                })
                .thenMany(receiver.consumeAutoAck(queueName))
                .onErrorMap(i -> {
                    i.printStackTrace();
                    return i;
                })
                .flatMap(m -> {
                            String msgString = new String(m.getBody());
                            log.info("for {} : {}", queueName, msgString);
                            try {
                                NotificationMessage message = objectMapper.readValue(msgString, NotificationMessage.class);
                                return Mono.just(objectMapper.writeValueAsString(message));
                            } catch (JsonProcessingException e) {
                                log.error(e.getMessage());
                                return Mono.just(msgString);
                            }
                        }
                );
    }
}
