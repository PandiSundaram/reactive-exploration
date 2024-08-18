package com.pandi.reactive_explore.sec02;

import com.pandi.reactive_explore.sec02.dto.OrderInfo;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {

    @Query("select p.* from customer c inner join customer_order co on c.id = co.customer_id inner join product p on co.product_id = p.id where name=:customerName")
    Flux<Product> findProductOrderedByCustomer(String customerName);

    @Query("select co.order_id,c.name AS customer_name,p.description AS product_name,co.amount,co.order_date from customer c inner join customer_order co on c.id = co.customer_id inner join product p on p.id=co.product_id where p.description=:description")
    Flux<OrderInfo> findProductOrderedByDescription(String description);



}
