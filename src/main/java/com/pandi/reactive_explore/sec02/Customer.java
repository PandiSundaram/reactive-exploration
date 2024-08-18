package com.pandi.reactive_explore.sec02;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@Data
public class Customer {

    @Id
    private Integer id;
    private String name;
    private String email;

}
