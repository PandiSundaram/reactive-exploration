package com.pandi.reactive_explore.sec03;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reactive")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public Flux<CustomerDto> findAllCustomer() {
        return customerService.findAllCustomer();
    }

    @GetMapping("/customers/page")
    public Flux<CustomerDto> findAllBypagination(@RequestParam (defaultValue = "0") Integer pageNumber,@RequestParam (defaultValue = "3") Integer count) {
        return customerService.findAllCustomerPagination(pageNumber,count);
    }

    @GetMapping("/customer/{id}")
    public Mono<CustomerDto> findByIdCustomer(@PathVariable Integer id) {
        return customerService.getByCustomerId(id);
    }

    @PutMapping("/customer/{id}")
    public Mono<CustomerDto> updateCustomer(@RequestBody Mono<CustomerDto> customerDto,@PathVariable  Integer id){

        return customerService.updateCustomer(customerDto,id);
    }

    @PostMapping("/customer")
    public Mono<CustomerDto> saveCustomer(@RequestBody @Valid Mono<CustomerDto> customerDtoMono){
        return customerService.saveCustomer(customerDtoMono);
    }

    @DeleteMapping("/customer/{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id){
        return customerService.deleteCustomerWithoutReturn(id);
    }

    @DeleteMapping("/customer/return/{id}")
    public Mono<Boolean> deleteCustomerwithReturn(@PathVariable Integer id){
        return customerService.deleteCustomerWithReturn(id);
    }


}
