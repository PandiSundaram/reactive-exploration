package com.pandi.reactive_explore.sec08;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@Table("product")
public class Product {

    @Id
    private Integer id;
    private String description;
    private Integer price;
}
