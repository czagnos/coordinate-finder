package com.example.coordinatefinder.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties("google")
@EnableEncryptableProperties
public class GoogleProperties {

    private String apiKey;
    private Long timeOut;
    private String baseUrl;
}
