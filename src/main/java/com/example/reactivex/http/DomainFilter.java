package com.example.reactivex.http;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

public class DomainFilter implements ExchangeFilterFunction {

    private final HttpProps httpProps;

    public DomainFilter(HttpProps httpProps) {
        this.httpProps = httpProps;
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        ClientRequest.Builder builder = ClientRequest.from(request);

        HttpHeaders httpHeaders = request.headers();
        String domainName = httpHeaders.getFirst("Domain-Name");
        if ("LaiHuoLa".equals(domainName)) {
            URI uri = request.url();
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(uri);
            uriComponentsBuilder.scheme(httpProps.getOneScheme());
            uriComponentsBuilder.host(httpProps.getOneHost());
            uriComponentsBuilder.port(httpProps.getOnePort());
            URI newUri = uriComponentsBuilder.build().toUri();
            builder.url(newUri);
        }
        return next.exchange(builder.build());
    }

}
