package com.pandi.reactive_explore.sec03;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

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

}
