package com.example.reactivex.laihuola;

import com.example.reactivex.utils.MonoUtils;
import com.example.reactivex.utils.MyEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Slf4j
@Service
public class LHLService {

    private final WebClient webClient;

    public LHLService(@Qualifier("PrimaryWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<LoginResp<UserData>> login(String phone, String captcha) {
        LoginBody body = new LoginBody(phone, captcha);
        return webClient.post()
                .uri("/api/login")
                .header("Authorization", "Bearer ")
                .header("Domain-Name", "LaiHuoLa")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), LoginBody.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<LoginResp<UserData>>() {
                })
                .doOnError(Throwable::printStackTrace)
                .onErrorReturn(LoginResp.error());
    }

    public void print(String phone, String captcha) {
        LoginBody body = new LoginBody(phone, captcha);
        webClient.post()
                .uri("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(body), LoginBody.class)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<LoginResp<UserData>>() {
                })
                .transformDeferred(MonoUtils.loginTransformer())
                .subscribe(new Consumer<UserData>() {
                    @Override
                    public void accept(UserData userData) {
                        log.info("subscribe: userId= " + userData.userId);
                        log.info("subscribe: userName= " + userData.userName);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        log.info("subscribe throwable");
                    }
                });
    }

}
