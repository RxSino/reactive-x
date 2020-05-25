package com.example.reactivex;

import com.example.reactivex.resp.BaseResp;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.function.Function;

@Slf4j
//@Component
public class MonoRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        onJust();
    }

    private void onFlatMap() {

    }

    private void onJust() {
        log.info("Mono: start: " + Thread.currentThread().getName());
        Mono<String> result = Mono.just(0)
                .subscribeOn(Schedulers.parallel())
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        log.info("Mono: map: " + Thread.currentThread().getName());
                        if (integer == 0) {
                            throw new RuntimeException();
                        }
                        return String.valueOf(integer + 1);
                    }
                })
                .onErrorReturn("error");

        log.info("Mono: result: " + result.block());

//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) {
//                        log.info("Mono: Consumer: " + Thread.currentThread().getName());
//                        System.out.println("consumer= " + s);
//                    }
//                }, Throwable::printStackTrace);

        log.info("Mono: end: " + Thread.currentThread().getName());
    }
}
