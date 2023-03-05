package ru.itmo.highload.stroroom.notificationsws.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.itmo.highload.stroroom.notificationsws.services.JwtHelper;
import ru.itmo.highload.stroroom.notificationsws.services.RabbitService;
import ru.itmo.highload.stroroom.notificationsws.dtos.TokenMessage;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReactiveWebSocketHandler implements WebSocketHandler {
    private final RabbitService rabbitService;
    private final ObjectMapper objectMapper;

    private final JwtHelper jwtHelper;

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        Flux<WebSocketMessage> messages = session.receive()
                .flatMap(this::authenticate)
                .flatMap(username -> rabbitService.listenQueue(username))
                .map(session::textMessage);
        return session.send(messages);
    }

    private Mono<String> authenticate(WebSocketMessage message) {
        String msg = message.getPayloadAsText();
        log.info("msg received: " + msg);
        try {
            TokenMessage token = objectMapper.readValue(msg, TokenMessage.class);
            Optional<String> username = jwtHelper.getUsernameIfValid(token);
            log.info("username: " + username);
            return Mono.just(username.orElseThrow());
        } catch (JsonProcessingException | NoSuchElementException e) {
            log.info(e.getMessage());
            return Mono.error(e);
        }
    }
}