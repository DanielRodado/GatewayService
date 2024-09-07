package com.example.gateway_service.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                if (jwtUtils.validateToken(token)) {
                    if (checkAuthorization(token, exchange)) {
                        return onErrorResponse(exchange, HttpStatus.UNAUTHORIZED); // Not authorized to access this resource
                    } else {
                        String username = jwtUtils.extractUsername(token);
                        exchange.getRequest().mutate().header("username", username).build();
                    }
                } else {
                    return onErrorResponse(exchange, HttpStatus.UNAUTHORIZED); // JWT token invalid
                }
            } catch (Exception ignore) {
                return onErrorResponse(exchange, HttpStatus.UNAUTHORIZED); // JWT token validation failed
            }
        } else {
            return onErrorResponse(exchange, HttpStatus.UNAUTHORIZED); // Header missing or invalid
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onErrorResponse(ServerWebExchange exchange, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }

    private boolean checkAuthorization(String token, ServerWebExchange exchange) {
        return jwtUtils.parseClaims(token).get("rol").equals("USER") && !exchange.getRequest().getURI().toString().contains("current");
    }
}