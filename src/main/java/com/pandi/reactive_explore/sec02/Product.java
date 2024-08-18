package com.pandi.reactive_explore.sec02;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@Data
public class Product {

    @Id
    private Integer id;
    private String description;
    private Integer price;


}
