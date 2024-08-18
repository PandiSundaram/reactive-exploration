package com.pandi.reactive_explore.sec03;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class validEmail implements ConstraintValidator<EmailRequired, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {

        if(s.contains("hotmail")){
            return false;
        }
        return true;
    }
}
