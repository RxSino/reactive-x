package com.example.reactivex.http;

import org.springframework.util.DigestUtils;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import java.util.Random;

public class SignatureFilter implements ExchangeFilterFunction {

    private String nonce() {
        int i = new Random().nextInt(9000) + 1000;
        return String.valueOf(i);
    }

    private String timestamp() {
        long timeMillis = System.currentTimeMillis();
        return String.valueOf(timeMillis / 1000);
    }

    private String signature(String timestamp, String nonce) {
        String token = "3649620B82877575A0B7D67DEBA008A0";
        String s = token + timestamp + nonce;
        return DigestUtils.md5DigestAsHex(s.getBytes());
    }

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
        ClientRequest.Builder builder = ClientRequest.from(request);
        String nonce = nonce();
        String timestamp = timestamp();
        String signature = signature(timestamp, nonce);
        builder.header("Signature", signature);
        builder.header("Timestamp", timestamp);
        builder.header("Nonce", nonce);
        return next.exchange(builder.build());
    }


}
