package com.pandi.reactive_explore.sec04.exception;

public class CustomerNotFound extends RuntimeException{

    public CustomerNotFound(String message){
        super(message);
    }
}
