package com.example.reactivex.web;

import com.example.reactivex.resp.BaseResp;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class TestHandler {

    public Mono<ServerResponse> testOk(ServerRequest request) {
        return ServerResponse.ok().body(Mono.just("ok"), String.class);
    }

    public Mono<ServerResponse> testRequest(ServerRequest request) {
        return request.queryParam("param")
                .map(param -> ServerResponse.ok().body(Mono.just(param), String.class))
                .orElse(ServerResponse.badRequest().build());
    }

    public Mono<ServerResponse> testJson(ServerRequest request) {
        return ServerResponse.ok()
                .body(Mono.just(BaseResp.success(null)), BaseResp.class);
    }


}
