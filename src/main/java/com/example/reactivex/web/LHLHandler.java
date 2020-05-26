package com.example.reactivex.web;

import com.example.reactivex.laihuola.LHLService;
import com.example.reactivex.utils.ResponseUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
public class LHLHandler {

    private final LHLService lhlService;

    public LHLHandler(LHLService lhlService) {
        this.lhlService = lhlService;
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        Optional<String> phone = request.queryParam("phone");
        Optional<String> captcha = request.queryParam("captcha");

//        if (!phone.isPresent()) {
//            return ServerResponse.badRequest().build();
//        }
//        if (!captcha.isPresent()) {
//            return ServerResponse.badRequest().build();
//        }
//        return lhlService.pureLogin(phone.get(), captcha.get());

        return phone.map(p1 ->
                captcha.map(p2 -> lhlService.pureLogin(p1, p2))
                        .orElseGet(ResponseUtils.badRequest()))
                .orElseGet(ResponseUtils.badRequest());
    }

}
