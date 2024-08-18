package com.pandi.reactive_explore.sec01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reactive")
@Slf4j
public class ReactiveController {

    private static final WebClient webclient = WebClient.builder().baseUrl("http://localhost:7070").build();

    @GetMapping(value = "/products")
    public Flux<Product> getProducts(){

       return webclient.get()
                .uri("/demo01/products").retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(p -> log.info("product"+ p));

    }

    @GetMapping(value = "/products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStream(){

        return webclient.get()
                .uri("/demo01/products").retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(p -> log.info("product"+ p));

    }

    @GetMapping(value = "/products/stream/notorious", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStreamNotorious(){

        return webclient.get()
                .uri("/demo01/products/notorious").retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(p -> log.info("product"+ p));

    }

}
