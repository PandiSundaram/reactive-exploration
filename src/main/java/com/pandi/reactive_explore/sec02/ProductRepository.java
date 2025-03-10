package com.pandi.reactive_explore.sec02;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {

    Flux<Product>findByPriceBetween(Integer start, Integer end);

    Flux<Product>findAllBy(Pageable pageable);
}
