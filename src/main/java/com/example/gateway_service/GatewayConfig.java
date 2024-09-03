package com.example.gateway_service;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator router(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userservice", r -> r.path("/api/users/**").uri("lb://user-service"))
                .route("taskservice", r -> r.path("/api/tasks/**").uri("lb://task-service"))
                .build();
    }

}
