package com.pandi.reactive_explore.sec08;

public class Mapper {

    public static Product DtoToEntity(ProductRequestDTO requestDTO){

        return new Product(requestDTO.id(), requestDTO.description(), requestDTO.price());

    }



}
