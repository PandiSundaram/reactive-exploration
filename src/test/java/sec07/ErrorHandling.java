package sec07;

import com.pandi.reactive_explore.ReactiveExploreApplication;
import com.pandi.reactive_explore.sec07.CalculatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest(classes = ReactiveExploreApplication.class)
@Slf4j
public class ErrorHandling {

    WebClient webClient = WebClient.builder().baseUrl("http://localhost:7070")
            .build();

    @Test
    public void onErrorReturn(){

        webClient.get().uri("/demo02/lec05/calculator/{a}/{b}",1,2)
                .header("operation",".")
                //.headers(httpHeaders -> httpHeaders.)
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
              //  .onErrorReturn(new CalculatorResponse(1,2,"*",0))   // return object
//                .onErrorResume(WebClientResponseException.NotFound.class, e->{
//                    return Mono.just(new CalculatorResponse(1,2,"*",24));
//                })  // return mono or flux
//                .doOnError(e -> log.info("e"+ e.getMessage()))  //side effect like logging and tracing
//                .retry(2)  // attempt 2 times incase of error
//                .doOnTerminate()//used for clean up activity like logging and closing connection execute always
//                .doFinally(e -> log.info("e"+ e)) // clean up activity same like terminate will get the detailed error
//                .onErrorComplete() // complete the stream after the error
              //  .doOnNext(p -> log.info("pandi"+ p))
               // .retry(2)
                .onErrorReturn(new CalculatorResponse(1,2,"*",0))
                .then()
                .as(StepVerifier::create)
              //  .expectNext(new CalculatorResponse(1,2,"+",3))
                .expectComplete()
                .verify();

    }

    @Test
    public void queryParamTest(){
       var query =  "first={first}&second={second}&operation={operation}";
        webClient.get()
                .uri(uriBuilder ->  uriBuilder.path("/demo02/lec06/calculator")
                        .query(query)
                        .build(1,2,"+"))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
              //  .then()
                .as(StepVerifier::create)
                .expectNext(new CalculatorResponse(1,2,"+",3))
                .expectComplete()
                .verify();


    }


}
