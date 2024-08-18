package sec02;


import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec02.CustomerOrderRepository;
import com.pandi.reactive_explore.sec02.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@Slf4j
public class CustomerOrderRepositoryTest {

    @Autowired
    CustomerOrderRepository customerOrderRepository;

    @Test
    public void getProductsByCustomerOrder(){

        customerOrderRepository.findProductOrderedByCustomer("sam")
                .doOnNext(result -> log.info("result"+ result))
                .as(StepVerifier::create)
                .assertNext(product -> Assertions.assertEquals("iphone 20",product.getDescription()))
                .assertNext(product -> Assertions.assertEquals("iphone 18", product.getDescription()))
                .expectComplete()
                .verify();

    }

    @Test
    public void getProductsByCustomerDescription(){

        customerOrderRepository.findProductOrderedByDescription("iphone 20")
                .doOnNext(result -> log.info("result"+ result))
                .as(StepVerifier::create)
                .assertNext(product -> Assertions.assertEquals("iphone 20",product.productName()))
                .assertNext(product -> Assertions.assertEquals("iphone 20",product.productName()))
                .expectComplete()
                .verify();

    }

}
