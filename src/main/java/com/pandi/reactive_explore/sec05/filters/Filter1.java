package com.pandi.reactive_explore.sec05.filters;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@Order(1)
public class Filter1 implements WebFilter {

    private static final Map<String,AuthType> AUTHMAP = Map.of("pandi123",AuthType.FREE,"pandi456",AuthType.PREMIUM);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        String token = exchange.getRequest().getHeaders().getFirst("jwt-token");
        if(!Objects.isNull(token) && AUTHMAP.containsKey(token)){
            exchange.getAttributes().put("category",AUTHMAP.get(token));
           return chain.filter(exchange);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"invalid token");
    }
}
