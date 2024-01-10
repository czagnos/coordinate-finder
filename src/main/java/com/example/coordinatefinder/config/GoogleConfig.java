package com.example.coordinatefinder.config;

import com.example.coordinatefinder.client.GoogleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
@RequiredArgsConstructor
public class GoogleConfig {

    private final GoogleProperties googleProperties;

    @Bean
    GoogleClient googleClient(final WebClient.Builder builder) {
        final HttpClient httpClient = HttpClient.newConnection()
                .responseTimeout(Duration.ofSeconds(googleProperties.getTimeOut()))
                .keepAlive(false);

        final WebClient webClient = builder.baseUrl(googleProperties.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        final WebClientAdapter webClientAdapter = WebClientAdapter.forClient(webClient);

        return HttpServiceProxyFactory.builder()
                .blockTimeout(Duration.ofSeconds(googleProperties.getTimeOut()))
                .clientAdapter(webClientAdapter)
                .build()
                .createClient(GoogleClient.class);

    }
}
