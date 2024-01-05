package com.example.coordinatefinder.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties("redis")
@Getter
@Setter
public class RedisProperties {
    private Map<String, Long> ttl;
}
