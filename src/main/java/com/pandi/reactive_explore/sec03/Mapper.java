package com.pandi.reactive_explore.sec03;

public class Mapper {


    public static CustomerDto toCustomerDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail());
    }

    public static Customer toCustomer(CustomerDto customerDto) {
        return new Customer(customerDto.id(), customerDto.name(), customerDto.email());
    }


}
