package com.pandi.reactive_explore.sec08;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductRepository extends ReactiveCrudRepository<Product,Integer> {
}
