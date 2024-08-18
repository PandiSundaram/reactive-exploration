package sec02;

import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec02.Customer;
import com.pandi.reactive_explore.sec02.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@Slf4j
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;


    @Test
    public void findAll(){

      Flux<Customer> customerFlux =  customerRepository.findAll();

      StepVerifier.create(customerFlux)
              .expectNextCount(10l)
              .expectComplete()
              .verify();
    }

    @Test
    public void findById(){

        customerRepository.findById(2)
                .doOnNext(p -> log.info("product {}"+ p) )
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike",c.getName()))
                .expectComplete()
                .verify();


    }

    @Test
    public void findByName(){
        Flux<Customer> customerFlux =customerRepository.findByName("mike").doOnNext(n -> log.info("{}"+n));

        StepVerifier.create(customerFlux)
                .expectNext()
                .assertNext(name -> Assertions.assertEquals("mike",name.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEnds(){

        customerRepository.findByEmailEndingWith(".com")
                .doOnNext(e -> log.info("{}"+e))
                .as(StepVerifier::create)
                .expectNextCount(10l)
                .expectComplete()
                .verify();

    }

    @Test
    public void saveAndDelete(){

        var customer = new Customer();
        customer.setName("pandi");
        customer.setEmail("pandi@gmail.com");

        customerRepository.save(customer)
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals(11,c.getId()))
                .expectComplete()
                .verify();
        customerRepository.deleteById(11)
                .then(customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();


    }

    @Test
    public void updateCustomer(){

        customerRepository.findByName("ethan")
                .doOnNext(c -> c.setName("pandi"))
                .flatMap(c -> customerRepository.save(c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("pandi",c.getName()))
                .verifyComplete();

    }
}
