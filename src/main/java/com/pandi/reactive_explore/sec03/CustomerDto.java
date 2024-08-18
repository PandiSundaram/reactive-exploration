package com.pandi.reactive_explore.sec03;

import jakarta.validation.constraints.*;

public record CustomerDto(

        Integer id,
        @NotBlank(message = "name cannot be null")
        @Size(min=2,max=50,message ="Name should be min 2 and max 50 charectors")
        String name,
        @EmailRequired
        @Pattern(regexp = "([a-z])+@([a-z])+\\.com", message = "Email should match the pattern a-z @ a-z .com")
        String email) {
}
