package com.pandi.reactive_explore.sec03;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = validEmail.class)
public @interface EmailRequired {

    String message() default "provide valid email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
