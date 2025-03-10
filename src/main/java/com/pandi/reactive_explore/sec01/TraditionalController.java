package com.pandi.reactive_explore.sec01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/traditional")
@Slf4j
public class TraditionalController {

  private static final RestClient restClient =
           RestClient.builder()
                   .baseUrl("http://localhost:7070").build();


  @GetMapping("/products")
  public List<Product> getProducts(){

        var products = restClient.get()
                 .uri("/demo01/products")
                 .retrieve()
                 .body(new ParameterizedTypeReference<List<Product>>() {

                 });

        log.info("response"+ products);
        return products;
  }

}
