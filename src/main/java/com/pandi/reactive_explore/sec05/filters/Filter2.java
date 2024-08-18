package com.pandi.reactive_explore.sec05.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static com.pandi.reactive_explore.sec05.filters.AuthType.FREE;
import static com.pandi.reactive_explore.sec05.filters.AuthType.PREMIUM;

@Service
@Slf4j
@Order(2)
public class Filter2 implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        AuthType authType = (AuthType) exchange.getAttributes().get("category");

        if(authType.equals(FREE)){
          if(HttpMethod.GET.equals(exchange.getRequest().getMethod()))
              return chain.filter(exchange);
          else
              throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        else if(authType.equals(PREMIUM)){
            return chain.filter(exchange);
        }

        else
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);

    }




}
