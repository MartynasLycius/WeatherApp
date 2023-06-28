package com.eastnetic.task.config;

import com.eastnetic.task.model.dto.LocationDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient getWebClient(){
        return WebClient.create();
    }
}
