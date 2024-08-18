package com.pandi.reactive_explore.sec08;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/reactive")
@Slf4j
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping(value= "/upload",consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<ProductResponsDTO> uploadProducts(@RequestBody Flux<ProductRequestDTO> productRequestDTOFlux){

        log.info("pandi invoked");
      return  productRequestDTOFlux.map(flux -> Mapper.DtoToEntity(flux)).doOnNext(d-> log.info("d"+ d))
                .as(flux -> productRepository.saveAll(flux))
                .then(productRepository.count())
                .map(c -> new ProductResponsDTO(UUID.randomUUID(),c));

    }

}
