package sec03;

import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec03.CustomerDto;
import com.pandi.reactive_explore.sec03.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@AutoConfigureWebTestClient
@Slf4j
public class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    @MockBean
    CustomerService customerService;

    @Test
    public void findAllCustomer(){

        //arrange
        CustomerDto customerDto = new CustomerDto(1,"pandi","pandi@gmail.com");
        CustomerDto customerDto1 = new CustomerDto(2,"sundaram","sundaram@gmail.com");


        //mock
        when(customerService.findAllCustomer()).thenReturn(Flux.just(customerDto,customerDto1));


        //perform

        webTestClient.get()
                .uri("/reactive/customers")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerDto.class)
                .value(customers -> log.info("customers {}"+ customers) )
                .hasSize(2);
    }

    @Test
    public void findAllCustomerpaginated(){

        //arrange
        CustomerDto customerDto = new CustomerDto(1,"pandi","pandi@gmail.com");
        CustomerDto customerDto1 = new CustomerDto(2,"sundaram","sundaram@gmail.com");
        CustomerDto customerDto2 = new CustomerDto(3,"sandi","pandi@gmail.com");

        //mock
        when(customerService.findAllCustomerPagination(any(),any())).thenReturn(Flux.just(customerDto,customerDto1,customerDto2));

        //perform
        webTestClient.get()
                .uri("/reactive/customers/page")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(CustomerDto.class)
                .consumeWith(value -> {
                    var datalist = value.getResponseBody();
                    log.info("size"+ datalist.size());
                    Assertions.assertEquals("pandi",datalist.get(0).name());
                });

    }


    @Test
    public void findByIdCustomerTest(){

        //arrange
        CustomerDto customerDto = new CustomerDto(1,"pandi","pandi@gmail.com");

        //mock
        when(customerService.getByCustomerId(any())).thenReturn(Mono.just(customerDto));

        webTestClient.get()
                .uri("/reactive/customer/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDto.class)
                .value(val -> log.info("value"+ val))
                .consumeWith( customer -> {
                  var customerresponse =  customer.getResponseBody();
                  Assertions.assertEquals("pandi",customerresponse.name());
                });
    }

    @Test
    public void updateCustomerTest(){

        //arrange
        var customerDto = new CustomerDto(null,"sundaram","pandi@gmail.com");

        //mock
        when(customerService.updateCustomer(any(),any())).thenReturn(Mono.just(customerDto));


        webTestClient.put()
                .uri("/reactive/customer/1")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDto.class)
                .consumeWith(c -> {
                     var cust =  c.getResponseBody();
                     Assertions.assertEquals("sundaram",cust.name());
                });
    }

    @Test
    public void createCustomer(){
        //arrange
        var customerDto = new CustomerDto(11,"sundar","pandi@gmail.com");

        //mock
        when(customerService.saveCustomer(any())).thenReturn(Mono.just(customerDto));


        webTestClient.post()
                .uri("/reactive/customer")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDto.class)
                .consumeWith(c -> {
                   var customer = c.getResponseBody();
                   Assertions.assertEquals(11,customer.id());
                });
    }

    @Test
    public void deleteCustomer(){

        //mock
        when(customerService.deleteCustomerWithoutReturn(any())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/reactive/customer/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }



}
