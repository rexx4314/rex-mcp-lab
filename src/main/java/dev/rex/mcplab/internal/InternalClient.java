package dev.rex.mcplab.internal;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Component
public class InternalClient {

    private final RestTemplate restTemplate;

    public InternalClient(RestTemplateBuilder builder) {
        // Phase1의 "실패/타임아웃" 테스트를 위해 타임아웃을 짧게 고정
        var factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) Duration.ofSeconds(1).toMillis());
        factory.setReadTimeout((int) Duration.ofSeconds(2).toMillis());

        this.restTemplate = builder
                .requestFactory(() -> factory)
                .build();
    }

    public String getStatus(int port) {
        return restTemplate.getForObject("http://localhost:" + port + "/internal/status", String.class);
    }

    public String getSlow(int port, long ms) {
        return restTemplate.getForObject("http://localhost:" + port + "/internal/slow?ms=" + ms, String.class);
    }
}
