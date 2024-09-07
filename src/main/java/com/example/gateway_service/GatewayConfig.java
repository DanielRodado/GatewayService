package com.example.gateway_service;

import com.example.gateway_service.configurations.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator router(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("userservice", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://user-service"))
                .route("userservice", r -> r.path("/api/auth/**")
                        .uri("lb://user-service"))
                .route("taskservice", r -> r.path("/api/tasks/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter))
                        .uri("lb://task-service"))
                .build();
    }

}
