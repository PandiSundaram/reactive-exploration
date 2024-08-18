package com.pandi.reactive_explore.sec02;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("customer_order")
@Data
public class CustomerOrder {

    @Id
    private UUID orderId;
    private Integer customerId;
    private Integer productId;
    private Instant orderDate;
    private Integer amount;





}
