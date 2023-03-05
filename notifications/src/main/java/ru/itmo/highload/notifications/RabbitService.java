package ru.itmo.highload.notifications;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.Sender;


@Service
@RequiredArgsConstructor
public class RabbitService {
    private final Sender rabbitSender;

    public Mono<Void> sendNotification(String queueName, String message) {
        return rabbitSender.declareQueue(QueueSpecification.queue(queueName).autoDelete(true))
                .then(rabbitSender.send(Mono.just(new OutboundMessage("", queueName, message.getBytes()))));

    }
}
