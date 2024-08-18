package sec05;

import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec05.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@AutoConfigureWebTestClient
@Slf4j
public class AuthTest {


    @Autowired
    WebTestClient webTestClient;
    // just validate HTTP response status codes!
    // unauthorized - no token
    // unauthorized - invalid token
    // standard category - GET - success
    // standard category - POST/PUT/DELETE - forbidden
    // prime category - GET - success
    // prime category - POST/PUT/DELETE - success

    @Test
    public void noToken(){

        webTestClient.get()
                .uri("/reactive/customer/1")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody().consumeWith(d -> log.info("data"+ d));
    }

    @Test
    public void invalidToken(){
        webTestClient.get()
                .uri("/reactive/customer/2")
                .header("jwt-token","1234")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .consumeWith(data-> log.info("data"+ data));
    }

    @Test
    public void freePlanTest(){
        webTestClient.get()
                .uri("/reactive/customer/2")
                .header("jwt-token","pandi123")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDto.class)
                .consumeWith(data -> log.info("data"+ data));
    }

    @Test
    public void freePlanForbiddenTest(){
        webTestClient.post()
                .uri("/reactive/customer")
                .bodyValue(new CustomerDto(null,"drai","durai@gmail.com"))
                .header("jwt-token","pandi123")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(CustomerDto.class)
                .consumeWith(data -> log.info("data"+ data));
    }

    @Test
    public void premiumPlanTest(){
        webTestClient.post()
                .uri("/reactive/customer")
                .bodyValue(new CustomerDto(null,"drai","durai@gmail.com"))
                .header("jwt-token","pandi456")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(CustomerDto.class)
                .consumeWith(data -> log.info("data"+ data));
    }




}
