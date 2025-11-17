package org.creditto.creditto_service.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "org.creditto.creditto_service")
public class FeignConfig {}
