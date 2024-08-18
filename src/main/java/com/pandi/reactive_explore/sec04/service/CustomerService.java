package com.pandi.reactive_explore.sec04.service;

import com.pandi.reactive_explore.sec04.dto.CustomerDto;
import com.pandi.reactive_explore.sec04.exception.CustomerNotFound;
import com.pandi.reactive_explore.sec04.mapper.Mapper;
import com.pandi.reactive_explore.sec04.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public Flux<CustomerDto> findAllCustomer() {
        log.info("i am in service");
        return this.customerRepository.findAll()
                .map(Mapper::toCustomerDto)
                .onErrorComplete();

    }

    public Flux<CustomerDto> findAllCustomerPagination(Integer pageNumber, Integer count) {

        return this.customerRepository.findAllBy(PageRequest.of(pageNumber,count).withSort(Sort.by("name").ascending()))
                .map(Mapper::toCustomerDto)
                .onErrorComplete();

    }


    public Mono<CustomerDto> getByCustomerId(Integer id){

          return this.customerRepository.findById(id)
                  .switchIfEmpty(Mono.error(new CustomerNotFound("not found for given"+id+"")))
                  .map(Mapper::toCustomerDto);
    }

    public Mono<CustomerDto> saveCustomer(Mono<CustomerDto> customerDto){

       return customerDto.map(Mapper::toCustomer).flatMap(m -> this.customerRepository.save(m))
                .map(Mapper::toCustomerDto);

    }

    public Mono<CustomerDto> updateCustomer(Mono<CustomerDto> customerDtoMono, Integer id){

          return this.customerRepository.findById(id)
                  .switchIfEmpty(Mono.error(new CustomerNotFound("not found for given"+id+"")))
                   .flatMap(entity -> customerDtoMono)
                   .map(Mapper::toCustomer)
                   .doOnNext(c -> c.setId(id))
                   .flatMap( c-> this.customerRepository.save(c))
                   .map(Mapper::toCustomerDto);

    }

    public Mono<Void> deleteCustomerWithoutReturn(Integer id){
       return this.customerRepository.deleteById(id);
    }

    public Mono<Boolean> deleteCustomerWithReturn(Integer id){
           return this.customerRepository.deleteCustomer(id)
                      .filter(d -> d.equals(true))
                      .defaultIfEmpty(false);

    }



}
