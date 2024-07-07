package com.proit.weather.pro_weather.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.proit.weather.pro_weather.domain")
@EnableJpaRepositories("com.proit.weather.pro_weather.repos")
@EnableTransactionManagement
public class DomainConfig {
}
