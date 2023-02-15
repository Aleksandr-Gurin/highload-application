package com.example.reactiveauthendpointaggregator.security;

import com.example.reactiveauthendpointaggregator.security.jwt.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {
    private final JwtTokenService tokenProvider;

    private Mono<String> resolveToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(t -> t.startsWith("Bearer "))
                .map(t -> t.substring(7));
    }

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return resolveToken(exchange)
                .filter(tokenProvider::validateToken)
                .flatMap(tokenProvider::getAuthentication);
    }

}
