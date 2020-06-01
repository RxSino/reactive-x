package com.example.reactivex;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ReactiveXApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveXApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(WebServerApplicationContext context) {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                System.out.println("applicationRunner");
                System.out.println(context.getWebServer().getClass().getName());
            }
        };
    }

    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event) {
        System.out.println("onWebServerReady");
        System.out.println(event.getWebServer().getClass().getName());
    }
}
