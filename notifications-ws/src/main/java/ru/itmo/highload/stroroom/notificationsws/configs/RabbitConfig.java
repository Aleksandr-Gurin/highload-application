package ru.itmo.highload.stroroom.notificationsws.configs;


import com.rabbitmq.client.Connection;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.*;


@Configuration
@RequiredArgsConstructor
public class RabbitConfig {

    final Mono<Connection> connectionMono;


    @Bean
    Sender rabbitSender() {
    return RabbitFlux.createSender(new SenderOptions().connectionMono(connectionMono));
}
    @Bean
    Receiver rabbitReceiver() {
        return RabbitFlux.createReceiver(new ReceiverOptions().connectionMono(connectionMono));
    }


}
