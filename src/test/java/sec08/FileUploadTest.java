package sec08;


import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec08.ProductRequestDTO;
import com.pandi.reactive_explore.sec08.ProductResponsDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.stream.IntStream;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@Slf4j
public class FileUploadTest {


    WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080")
            .build();


    @Test
    public void streamToBlokUpload(){

       Flux<ProductRequestDTO> requestFlux = Flux.range(1,100)
                       .map(d -> new ProductRequestDTO(null,d+"description",d));

        webClient.post()
                .uri("/reactive/upload")
                .contentType(MediaType.APPLICATION_NDJSON)
                .body(requestFlux,ProductRequestDTO.class)
                .retrieve()
                .bodyToMono(ProductResponsDTO.class)
                .then()
                .doOnNext(d -> log.info("data"+ d))
                .as(StepVerifier::create)
                .expectNext()
                .expectComplete()
                .verify();


    }

    public void blockToStream(){

    }

    public void streamToStream(){

    }


}
