package com.pandi.reactive_explore.sec05.repository;

import com.pandi.reactive_explore.sec05.entities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer,Integer> {

    @Modifying
    @Query("delete customer c where c.id=:id")
    Mono<Boolean> deleteCustomer(Integer id);

    Flux<Customer> findAllBy(Pageable pageable);

}
