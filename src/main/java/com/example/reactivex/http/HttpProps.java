package com.example.reactivex.http;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rx.http")
public class HttpProps {

    private String oneBaseUrl;
    private String oneScheme;
    private String oneHost;
    private int onePort;

    private String twoBaseUrl;
    private String twoScheme;
    private String twoHost;
    private int twoPort;

}
