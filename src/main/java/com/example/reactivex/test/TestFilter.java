package com.example.reactivex.test;

import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

public class TestFilter implements ExchangeFilterFunction {

    private String timestamp() {
        long timeMillis = System.currentTimeMillis();
        return String.valueOf(timeMillis);
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        ClientRequest.Builder builder = ClientRequest.from(request);
        builder.header("Timestamp", timestamp());
        return next.exchange(builder.build());
    }


}
