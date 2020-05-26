package com.example.reactivex.utils;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.function.Supplier;

public class ResponseUtils {

    public static Supplier<Mono<ServerResponse>> badRequest() {
        return () -> ServerResponse.badRequest().build();
    }

    public static Function<ClientResponse, Mono<? extends ServerResponse>> asString() {
        return response -> {
            Mono<String> stringMono = response.bodyToMono(String.class);
            return ServerResponse.ok().body(stringMono, String.class);
        };
    }

    public static Function<ClientResponse, Mono<? extends ServerResponse>> asDataBuffer() {
        return response -> {
            Flux<DataBuffer> body = response.body(BodyExtractors.toDataBuffers());
            return ServerResponse.ok().body(BodyInserters.fromDataBuffers(body));
        };
    }

}
