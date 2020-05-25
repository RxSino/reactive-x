package com.example.reactivex.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    private static final String baseUrl = "http://localhost:9999";

    private WebClient webClient = WebClient
            .builder()
            .baseUrl(baseUrl)
            .filter(new TestFilter())
            .build();

    public Mono<String> request() {
        return webClient.get()
                .uri("/jpa/timestamp")
                .retrieve()
                .bodyToMono(String.class);
    }


}
