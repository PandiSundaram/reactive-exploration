package com.pandi.reactive_explore.sec04.advice;


import com.pandi.reactive_explore.sec04.exception.CustomerNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
@Slf4j
public class CustomerContollerAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail webExchangeBindExceptionHandler(WebExchangeBindException e) {
        var errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .findAny();

        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, errors.get());
        problemDetail.setTitle("input argument not valid");

        return problemDetail;
    }

    @ExceptionHandler(CustomerNotFound.class)
    public ProblemDetail customerNotFoundHandler(CustomerNotFound e){
       var problemDetail =ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,e.getMessage()) ;
        problemDetail.setTitle("Customer Not Found");
       return problemDetail;
    }

}
