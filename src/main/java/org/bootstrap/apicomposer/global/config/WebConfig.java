package org.bootstrap.apicomposer.global.config;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public WebProperties webProperties() {
        return new WebProperties();
    }
}
