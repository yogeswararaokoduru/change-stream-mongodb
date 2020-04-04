package com.org.yogi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties("yogi.mongodb")
public class ChangeStreamConfig {
    private String collection;
}
