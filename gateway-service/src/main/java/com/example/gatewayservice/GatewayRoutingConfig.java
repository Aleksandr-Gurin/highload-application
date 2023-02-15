package com.example.gatewayservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class GatewayRoutingConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route.path("/auth")
                        .uri("lb://user-service"))
                .route(route -> route.path("/api/v1/subscription/**")
                        .uri("lb://facade"))
                .route(route -> route.path("/api/v1/user-subscription/**")
                        .uri("lb://facade"))
                .route(route -> route.path("/api/v1/ticket/**")
                        .uri("lb://facade"))
                .route(route -> route.path("/api/v1/user-playlist/**")
                        .uri("lb://facade"))
                .route(route -> route.path("/**")
                        .uri("lb://reactive-facade"))
                .build();


    }
}


