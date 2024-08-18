package sec04;

import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec04.dto.CustomerDto;
import com.pandi.reactive_explore.sec04.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@AutoConfigureWebTestClient
@Slf4j
public class sec04 {

    @Autowired
    WebTestClient webTestClient;



    @Test
    public void inputValidationTest(){

        var customerDto = new CustomerDto(null,"d","sundaram@gmail.com");


        webTestClient.post()
                .uri("/reactive/customer")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ProblemDetail.class)
                .value(details -> log.info("details"+ details));

    }

    @Test
    public void inputValidationAnnotationTest(){

        var customerDto = new CustomerDto(null,"durai","sundaram@hotmail.com");


        webTestClient.post()
                .uri("/reactive/customer")
                .bodyValue(customerDto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ProblemDetail.class)
                .value(details -> log.info("details"+ details));

    }


    @Test
    public void userDefinedExceptionTest(){


        webTestClient.get()
                .uri("/reactive/customer/20")
                .exchange()
                .expectStatus().is4xxClientError();

    }



}


