package com.example.reactivex.http;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import javax.net.ssl.SSLException;

@Configuration
public class HttpConfiguration {

    @Bean
    public SslContext sslContext() throws SSLException {
        return SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build();
    }

    @Bean
    public HttpClient httpClient(SslContext sslContext) {
        return HttpClient
                .create()
                .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
    }

    @Bean
    public ClientHttpConnector clientHttpConnector(HttpClient httpClient) {
        return new ReactorClientHttpConnector(httpClient);
    }

    @Bean
    public DomainFilter domainFilter(HttpProps httpProps) {
        return new DomainFilter(httpProps);
    }

    @Bean
    public SignatureFilter signatureFilter() {
        return new SignatureFilter();
    }

    @Bean("PrimaryWebClient")
    public WebClient webClient(ClientHttpConnector clientHttpConnector,
                               SignatureFilter signatureFilter,
                               HttpProps httpProps) {
        return WebClient
                .builder()
                .baseUrl(httpProps.getOneBaseUrl())
                .clientConnector(clientHttpConnector)
                .filter(signatureFilter)
                .build();
    }

    @Bean("SecondaryWebClient")
    public WebClient secondWebClient(ClientHttpConnector clientHttpConnector,
                                     SignatureFilter signatureFilter,
                                     HttpProps httpProps) {
        return WebClient
                .builder()
                .baseUrl(httpProps.getTwoBaseUrl())
                .clientConnector(clientHttpConnector)
                .filter(signatureFilter)
                .build();
    }

}
