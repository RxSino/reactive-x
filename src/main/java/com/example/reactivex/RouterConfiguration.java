package com.example.reactivex;

import com.example.reactivex.web.LHLHandler;
import com.example.reactivex.web.TestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<?> testRouter(TestHandler testHandler) {
        return route()
                .GET("/api/test/ok", testHandler::testOk)
                .GET("/api/test/request", testHandler::testRequest)
                .GET("/api/test/json", testHandler::testJson)
                .build();
    }

    @Bean
    public RouterFunction<?> lhlRouter(LHLHandler lhlHandler) {
        return route()
                .GET("/api/lhl/login", lhlHandler::login)
                .build();
    }

}
