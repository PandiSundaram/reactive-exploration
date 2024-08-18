package sec02;

import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec02.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@Slf4j
public class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void priceBetween(){

        productRepository.findByPriceBetween(750,1000)
                .as(StepVerifier::create)
                .expectNextCount(3L)
                .verifyComplete();
    }
    @Test
    public void pageable(){
        Pageable pageRequest = PageRequest.of(0, 3).withSort(Sort.by("price").ascending());
        productRepository.findAllBy(pageRequest)
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(200, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(250, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(300, p.getPrice()))
                .expectComplete()
                .verify();
    }



}
